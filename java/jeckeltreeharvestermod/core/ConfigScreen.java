package jeckeltreeharvestermod.core;

import jeckelcorelibrary.base.configs.AConfigScreen;
import net.minecraft.client.gui.GuiScreen;

public class ConfigScreen extends AConfigScreen
{
	public ConfigScreen(GuiScreen parent)
	{
		super(parent, Refs.ModId, Refs.ModName, Refs.getConfigHandler().getConfig());
	}

}
