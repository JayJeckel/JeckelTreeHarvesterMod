package jeckeltreeharvestermod.content.treeharvester.client;

import java.awt.Point;
import java.awt.Rectangle;

import jeckelcorelibrary.base.guis.AScreenTileInventory;
import jeckelcorelibrary.core.BlockPosition;
import jeckeltreeharvestermod.content.treeharvester.ContainerTreeHarvester;
import jeckeltreeharvestermod.content.treeharvester.TileTreeHarvester;
import jeckeltreeharvestermod.core.Refs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ScreenTreeHarvester extends AScreenTileInventory<TileTreeHarvester>
{
	public ScreenTreeHarvester(EntityPlayer player, TileTreeHarvester tile)
	{
		super(player, tile, new ContainerTreeHarvester(player, tile), tile, 176, 185);
		this.setResourceLocation(Refs.ModId, "tree_harvester.png");
	}

	private final Rectangle rectTank = new Rectangle(28, 31, 16, 47);
	private final Rectangle rectStatusLight = new Rectangle(153, 25, 11, 11);

	//private final Rectangle rectLevelMeter = new Rectangle(143, 28, 4, 31);

	private final OverlayInfo infoTankExchanger = new OverlayInfo(new Rectangle(12, 50, 8, 9), new Point(0, 166), new Point(0, 166), false, false, false);
	private final OverlayInfo infoTreeChopper = new OverlayInfo(new Rectangle(68, 44, 16, 16), new Point(176, 0), new Point(176, 16), true, false, false);

	@Override protected void doDrawTitle() { this.drawTextCenter(this.getTitle(), 5); }

	@Override protected void onDrawTexts()
	{
		final int cap = this._tile.getTank().getCapacity();
		final int amount = this._tile.getTank().getFluidAmount();
		final double percent = ((double)amount / (double)cap) * 100.0D;
		this.drawTextLeft("" + cap, 46, 30);
		this.drawTextLeft("" + amount, 46, 50);
		this.drawTextLeft(String.format("%.1f", percent) + "%", 46, 70);

		this.drawTextLeft(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 93);
	}

	@Override protected void onDrawOverlays()
	{
		final int statusTile = this._tile.getChoppingStatus();
		this.drawImage(this.rectStatusLight, new Point(205, 11 * statusTile));

		final BlockPosition posTarget = this._tile.getTargetPosition();
		final int level = this._tile.getTargetLevel();

		ItemStack[][] stacks = (posTarget == null ? null : this._tile.getTargetGrid(level));
		BlockPosition[][] positions = (posTarget == null ? null : this._tile.getPositionGrid(level));

		final int[] cols = new int[] { 0, 2, 4, 3, 1 };
		//final int[] rows = new int[] { 1, 2, 3, 4, 5 };
		final int xStart = 148;
		final int yStart = 38;

		for (int col = 0; col < 5; col++)
		{
			for (int row = 0; row < 5; row++)
			{
				final int x = xStart + (4 * col);
				final int y = yStart + (4 * row);
				final int status = (posTarget == null || stacks[cols[col]][row] == null ? 0 : (posTarget.equals(positions[cols[col]][row]) ? 2 : 1));
				this.drawImage(new Rectangle(x, y, 5, 5), new Point(198, 5 * status));
			}
		}

		final Rectangle rectLevel = new Rectangle(144, 56 - ((2 * level) + (1 * level)), 2, 2);
		final Point pointLevel = new Point(203, 0 + (2 * statusTile));
		this.drawImage(rectLevel, pointLevel);

		if (this._tile.getTank().getFluidAmount() > 0) { this.drawFluidTank(this.rectTank, this._tile.getTank()); }

		if (this._tile.tankExchanger.isProcessing()) { this.drawProcessOverlay(this._tile.tankExchanger, this.infoTankExchanger); }
		if (this._tile.treeChopperTick.isProcessing()) { this.drawProcessOverlay(this._tile.treeChopperTick, this.infoTreeChopper); }
	}

	@Override protected void onDrawTooltips(int x, int y)
	{
		if (this.rectTank.contains(x, y)) { this.drawTankTooltip(x, y, this._tile.getTank()); }
		else if (this.infoTankExchanger.contains(x, y)) { this.drawProcessTooltip(x, y, "Tank Exchanging", this._tile.tankExchanger); }
		else if (this.infoTreeChopper.contains(x, y)) { this.drawProcessTooltip(x, y, "Tree Chopping", this._tile.treeChopperTick); }
		/*else if (this.rectLevelMeter.contains(x, y))
		{
			final int level = this._tile.getTargetLevel();
			this.drawTooltip(x, y, "Target", "Level: " + level);
		}*/
	}
}
