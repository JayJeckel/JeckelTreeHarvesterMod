package jeckeltreeharvestermod.proxy;

import jeckelcorelibrary.utils.GameRegUtil;
import jeckeltreeharvestermod.content.ContentManager;
import jeckeltreeharvestermod.content.treeharvester.TileTreeHarvester;
import jeckeltreeharvestermod.content.treeharvester.client.BlockRendererTreeHarvester;

public class ClientProxy extends CommonProxy
{
	@Override public boolean isClient() { return true; }

	@Override public void initialize(final String modId)
	{
		super.initialize(modId);

		GameRegUtil.Client.registerTileRenderer(ContentManager.ModBlocks.tree_harvester, new TileTreeHarvester(true), new BlockRendererTreeHarvester());
	}
}
