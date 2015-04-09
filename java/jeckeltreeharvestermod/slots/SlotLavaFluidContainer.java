package jeckeltreeharvestermod.slots;

import jeckelcorelibrary.core.slots.SlotLiquidContainer;
import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class SlotLavaFluidContainer extends SlotLiquidContainer
{
    public SlotLavaFluidContainer(IInventory inventory, int slotIndex, int x, int y)
    {
        this(inventory, slotIndex, x, y, true, true, true);
    }

    public SlotLavaFluidContainer(IInventory inventory, int slotIndex, int x, int y, boolean useIcon)
    {
        this(inventory, slotIndex, x, y, true, true, useIcon);
    }

    public SlotLavaFluidContainer(IInventory inventory, int slotIndex, int x, int y, boolean allowEmpty, boolean allowFilled)
    {
        this(inventory, slotIndex, x, y, allowEmpty, allowFilled, true);
    }

    public SlotLavaFluidContainer(IInventory inventory, int slotIndex, int x, int y, boolean allowEmpty, boolean allowFilled, boolean useIcon)
    {
    	super(inventory, slotIndex, x, y, allowEmpty, allowFilled, useIcon);
    }

    @Override public boolean isItemValid(ItemStack stack)
    {
    	if (stack == null) { return false; }
    	else if (FluidUtil.isEmptyContainer(stack)) { return this.allowEmpty; }
    	else if (FluidUtil.isFilledContainer(stack) && this.allowFilled)
    	{
    		return FluidUtil.getFluid(stack).isFluidEqual(new FluidStack(FluidRegistry.LAVA, 1));
    	}
    	return false;
    }
}
