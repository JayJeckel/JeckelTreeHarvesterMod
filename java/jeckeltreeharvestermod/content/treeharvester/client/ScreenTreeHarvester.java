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
		super(player, tile, new ContainerTreeHarvester(player, tile), tile, 176, 166);
		this.setResourceLocation(Refs.ModId, "tree_harvester.png");
	}

	private Rectangle rectTank = new Rectangle(28, 22, 16, 47);

	private final OverlayInfo infoTankExchanger = new OverlayInfo(new Rectangle(12, 41, 8, 9), new Point(0, 166), new Point(0, 166), false, false, false);
	private final OverlayInfo infoTreeChopper = new OverlayInfo(new Rectangle(109, 35, 16, 16), new Point(176, 0), new Point(176, 16), true, false, false);

	@Override protected void onDrawTexts()
	{
		final int cap = this._tile.getTank().getCapacity();
		final int amount = this._tile.getTank().getFluidAmount();
		final double percent = ((double)amount / (double)cap) * 100.0D;
		this.drawTextLeft("" + cap, 46, 23);
		this.drawTextLeft("" + amount, 46, 43);
		this.drawTextLeft(String.format("%.1f", percent) + "%", 46, 63);

		this.drawTextLeft(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2);
	}

	@Override protected void onDrawOverlays()
	{
		//this.drawItemStack(this._tile.getTargetStack(), 52, 23, null);

		//final boolean powered = this._tile.getWorldObj().isBlockIndirectlyGettingPowered(this._tile.xCoord, this._tile.yCoord, this._tile.zCoord);
		//final boolean targeted = this._tile._targetLoc != null;
		//final int statusTile = (!targeted ? 0 : (powered ? 1 : 2));
		final int statusTile = this._tile.getChoppingStatus();
		this.drawImage(new Rectangle(86, 20, 11, 11), new Point(205, 11 * statusTile));

		final BlockPosition posTile = this._tile.getBlockPosition();
		final BlockPosition posTarget = this._tile.getTargetPosition();

		int level = 0;
		ItemStack[][] stacks = null;
		BlockPosition[][] positions = null;
		if (posTarget != null)
		{
			level = posTarget.y - posTile.y;
			stacks = this._tile.getTargetGrid(level);
			positions = this._tile.getPositionGrid(level);
		}

		final int[] cols = new int[] { 0, 2, 4, 3, 1 };
		//final int[] rows = new int[] { 1, 2, 3, 4, 5 };
		final int xStart = 81;
		final int yStart = 33;

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

		this.drawImage(new Rectangle(77, 51 - ((2 * level) + (1 * level)), 2, 2), new Point(203, 0 + (2 * statusTile)));

		if (this._tile.getTank().getFluidAmount() > 0) { this.drawFluidTank(this.rectTank, this._tile.getTank()); }

		if (this._tile.tankExchanger.isProcessing()) { this.drawProcessOverlay(this._tile.tankExchanger, this.infoTankExchanger); }
		if (this._tile.treeChopperTick.isProcessing()) { this.drawProcessOverlay(this._tile.treeChopperTick, this.infoTreeChopper); }
	}

	@Override protected void onDrawTooltips(int x, int y)
	{
		if (this.rectTank.contains(x, y)) { this.drawTankTooltip(x, y, this._tile.getTank()); }
		else if (this.infoTankExchanger.contains(x, y)) { this.drawProcessTooltip(x, y, "Tank Exchanging", this._tile.tankExchanger); }
		else if (this.infoTreeChopper.contains(x, y)) { this.drawProcessTooltip(x, y, "Tree Chopping", this._tile.treeChopperTick); }
	}

	/*private void drawItemStack(ItemStack stack, int x, int y, String p_146982_4_)
	{
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if (stack != null) font = stack.getItem().getFontRenderer(stack);
		if (font == null) font = fontRendererObj;
		GL11.glPushMatrix();
		GL11.glScaled(0.5, 0.5, 1.0);
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, 2 * (x + this.guiLeft), 2 * (y + this.guiTop));
		//itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack == null ? 0 : 8), p_146982_4_);
		//GL11.glTranslatef(1.50F, 1.50F, 0.0F);
		GL11.glPopMatrix();
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}*/
}
