package jeckeltreeharvestermod.core;

import net.minecraft.client.gui.GuiScreen;
import jeckelcorelibrary.base.configs.AConfigFactory;

public class ConfigFactory extends AConfigFactory
{
	@Override public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigScreen.class; }
}
