package jeckeltreeharvestermod.content;

import jeckelcorelibrary.GlobalRefs;
import jeckelcorelibrary.api.managers.IContentManager;
import jeckelcorelibrary.utils.GameRegUtil;
import jeckeltreeharvestermod.content.items.ItemSawBlade;
import jeckeltreeharvestermod.content.treeharvester.BlockTreeHarvester;
import jeckeltreeharvestermod.content.treeharvester.ItemBlockTreeHarvester;
import jeckeltreeharvestermod.content.treeharvester.TileTreeHarvester;
import jeckeltreeharvestermod.core.Refs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContentManager implements IContentManager
{
	public static class ModBlocks
	{
		public static BlockTreeHarvester tree_harvester;
	}

	public static class ModItems
	{
		public static Item saw_blade;
	}

	@Override public void pre()
	{
		ModItems.saw_blade = new ItemSawBlade();
		GameRegUtil.item(ModItems.saw_blade);

		ModBlocks.tree_harvester = new BlockTreeHarvester();
		GameRegUtil.block(ModBlocks.tree_harvester, ItemBlockTreeHarvester.class, TileTreeHarvester.class);

		GlobalRefs.getTabManager().addMachineBlock(Refs.ModId, ModBlocks.tree_harvester);
		GlobalRefs.getTabManager().addMachineItem(Refs.ModId, ModItems.saw_blade);
	}

	@Override public void initialize()
	{
		GameRegUtil.recipeShaped(new ItemStack(ModItems.saw_blade, 9, 0),
				"# #",
				" @ ",
				"# #",
				'@', new ItemStack(Blocks.redstone_block),
				'#', new ItemStack(Items.iron_ingot));
		GameRegUtil.recipeShaped(new ItemStack(ModItems.saw_blade, 1, 0),
				"# #",
				" @ ",
				"# #",
				'@', new ItemStack(Items.redstone),
				'#', "nuggetIron");

		GameRegUtil.recipeShaped(new ItemStack(ModBlocks.tree_harvester, 1, 0),
				"LIL",
				"GTB",
				"LIL",
				'B', new ItemStack(ModItems.saw_blade),
				'T', new ItemStack(Blocks.redstone_torch),
				'G', "blockGlass",
				'L', "logWood",
				'I', new ItemStack(Items.iron_ingot));
	}

	@Override public void post()
	{
	}
}
