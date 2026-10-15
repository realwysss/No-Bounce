package dev.nobn;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CfgScr {
	static Component t(String s) {
		return Component.literal(s);
	}

	public static Screen mk(Screen p) {
		ConfigBuilder b = ConfigBuilder.create().setParentScreen(p).setTitle(t("No Bounce")).setSavingRunnable(Cfg::sv);
		ConfigCategory cc = b.getOrCreateCategory(t("Settings"));
		ConfigEntryBuilder e = b.entryBuilder();
		Cfg c = Cfg.c;
		cc.addEntry(e.startBooleanToggle(t("No Bounce"), c.nb).setDefaultValue(true).setTooltip(t("Removes the jump your hotbar item does when the server gives items back, like crystals on high ping.")).setSaveConsumer(v -> c.nb = v).build());
		cc.addEntry(e.startBooleanToggle(t("Fade Number"), c.fd).setDefaultValue(true).setTooltip(t("The stack number fades into the new number instead of snapping to it.")).setSaveConsumer(v -> c.fd = v).build());
		cc.addEntry(e.startIntSlider(t("Fade Time"), c.ms, 20, 1000).setDefaultValue(150).setTextGetter(v -> t(v + " ms")).setTooltip(t("How long the number takes to fade.")).setSaveConsumer(v -> c.ms = v).build());
		return b.build();
	}
}
