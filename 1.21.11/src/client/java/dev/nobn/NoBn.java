package dev.nobn;

import java.util.HashMap;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NoBn implements ClientModInitializer {
	static class S {
		Item it;
		int now;
		int old;
		long at;
		long seen;
	}

	static final HashMap<Long, S> M = new HashMap<>();
	public static int fades;

	public void onInitializeClient() {
		Cfg.ld();
	}

	public static boolean fade(GuiGraphics g, Font f, ItemStack st, int x, int y) {
		long t = System.nanoTime();
		long k = ((long) x << 20) ^ (long) y;
		S s = M.computeIfAbsent(k, z -> new S());
		int c = st.getCount();
		if (s.it != st.getItem() || t - s.seen > 400000000L) {
			s.it = st.getItem();
			s.now = c;
			s.old = c;
			s.at = 0;
		} else if (s.now != c) {
			s.old = s.now;
			s.now = c;
			s.at = t;
		}
		s.seen = t;
		float p = s.at == 0 ? 1f : (t - s.at) / (Cfg.c.ms * 1000000f);
		if (p >= 1f || s.old == s.now) return false;
		fades++;
		int ao = Math.max(8, Math.round(255 * (1f - p)));
		int an = Math.max(8, Math.round(255 * p));
		if (s.old != 1) one(g, f, s.old, x, y, ao);
		if (s.now != 1) one(g, f, s.now, x, y, an);
		return true;
	}

	static void one(GuiGraphics g, Font f, int n, int x, int y, int a) {
		String s = String.valueOf(n);
		g.drawString(f, s, x + 19 - 2 - f.width(s), y + 6 + 3, a << 24 | 0xffffff, true);
	}
}
