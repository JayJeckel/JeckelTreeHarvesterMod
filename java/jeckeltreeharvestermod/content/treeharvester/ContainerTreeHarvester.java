package jeckeltreeharvestermod.content.treeharvester;

import jeckelcorelibrary.base.guis.AContainerTileInventory;
import jeckelcorelibrary.core.slots.SlotOutput;
import jeckeltreeharvestermod.slots.SlotLavaFluidContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerTreeHarvester extends AContainerTileInventory<TileTreeHarvester>
{
	public ContainerTreeHarvester(EntityPlayer player, TileTreeHarvester tile)
	{
		super(player, tile, tile, 176, 166);

		// Liquid Container Input
		this.addSlotToContainer(new SlotLavaFluidContainer(tile, 0, 8, 22));

		// Liquid Container Output
		this.addSlotToContainer(new SlotOutput(tile, 1, 8, 53));

		// Internal Inventory
		this.addInventorySlots(this._inventory, 134, 17, 2, 3, 2, SlotOutput.class);

		// Liquid Container Output
		//this.addSlotToContainer(new SlotDisplay(tile, 11, 82, 53));

		// Player Inventory
		this.addPlayerInventorySlots(this._player.inventory, 8, this._height);
		this.addPlayerHotbarSlots(this._player.inventory, 8, this._height);
	}

	@Override protected int getMergeSlotCount(final int slotIndex)
	{
		switch (slotIndex) { case 0: { return 1; } default: { return 0; } }
	}

	@Override protected boolean isValidSlotItem(final EntityPlayer player, final int slotIndex, final ItemStack stack)
	{
		return this.getSlot(slotIndex).isItemValid(stack);
	}
}
