package jeckeltreeharvestermod.content.treeharvester;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockTreeHarvester extends ItemBlock
{
	public ItemBlockTreeHarvester(Block block)
	{
		super(block);
		this.setMaxStackSize(1);
	}
	
	public Block getBlock() { return this.field_150939_a; }

	@Override public String getUnlocalizedName(ItemStack stack)
	{
		return this.getBlock().getUnlocalizedName();
	}

	@Override public int getMetadata(int meta) { return 0; }

    @SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
    @Override public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List infoList, boolean par4)
    {
    	infoList.add("Machine capable of automatically harvesting trees.");
    	infoList.add("Area: 5x10x5");
    	int amount = 0;
    	int capacity = 1000;
    	if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tile"))
    	{
    		capacity = stack.getTagCompound().getCompoundTag("tile").getCompoundTag("tank").getInteger("capacity");
    		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tile").getCompoundTag("tank").getCompoundTag("fluid");
            if (!tag.hasKey("Empty"))
            {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag);
                if (fluid != null) { amount = fluid.amount; }
            }
    	}
		infoList.add("Fuel: " + amount + "/" + capacity + " mb");
    }

    @Override public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
    	if (!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound()); }
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.setInteger("ammount", 0);
    	tag.setInteger("capacity", 0);
    	stack.stackTagCompound.setTag("tank", tag);
    }
}
