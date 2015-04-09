package jeckeltreeharvestermod.content.treeharvester;

import java.util.Random;

import jeckelcorelibrary.base.blocks.ABlockTile;
import jeckelcorelibrary.utils.FluidUtil;
import jeckeltreeharvestermod.core.Refs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTreeHarvester extends ABlockTile
{
	public BlockTreeHarvester()
	{
		super(Refs.ModId, "tree_harvester", Material.iron, Block.soundTypeMetal);
	}

	@Override public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileTreeHarvester();
	}

	@Override public boolean canHarvestBlock(EntityPlayer player, int meta) { return true; }

	@Override public int getRenderType() { return -1; }

	@Override public boolean isOpaqueCube() { return false; }

	@Override public boolean renderAsNormalBlock() { return false; }

	@Override public int quantityDropped(Random p_149745_1_) { return 0; }

	@Override public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, living, stack);
		TileTreeHarvester tile = (TileTreeHarvester) world.getTileEntity(x, y, z);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("tile"))
		{
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("tile");
			tile.readNBTInventory(tag);
			//tile.readNBTTank(tag);
			for (int index = 0; index < tile.getTanks().size(); index++) { FluidUtil.readNBTTank(tile.getTanks().get(index), "tank_" + index, tag); }
		}
	}

	@Override public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileTreeHarvester tile = (TileTreeHarvester) world.getTileEntity(x, y, z);
		//if (tile != null && tile instanceof IInventory) { InvUtil.dropInventory((IInventory)tile, world, x, y, z); }
		Item item = Item.getItemFromBlock(this);
		if (item != null)// && !world.isRemote)
		{
			//BasicFluidTank tank = tile.getTank();
			//int capacity = tank.getCapacity();
			//int amount = tank.getFluidAmount();
			ItemStack stack = new ItemStack(item, 1, 0);

			NBTTagCompound tag;
			if (!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound()); }

			tag = new NBTTagCompound();
			tile.writeNBTInventory(tag);
			//tile.writeNBTTank(tag);
			for (int index = 0; index < tile.getTanks().size(); index++) { FluidUtil.writeNBTTank(tile.getTanks().get(index), "tank_" + index, tag); }
			stack.stackTagCompound.setTag("tile", tag);

			this.dropBlockAsItem(world, x, y, z, stack);
		}
		world.removeTileEntity(x, y, z);
	}


	// ##################################################
	//
	// Icon Methods
	//
	// ##################################################

	@SideOnly(Side.CLIENT)
	@Override public void registerBlockIcons(IIconRegister reg)
	{
		this.blockIcon = reg.registerIcon(this.textureName);
	}

	@SideOnly(Side.CLIENT)
	@Override public IIcon getIcon(int side, int meta)
	{
		return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		return this.blockIcon;
	}


	// ##################################################
	//
	// Particle Methods
	//
	// ##################################################

	@SideOnly(Side.CLIENT)
	@Override public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null || !(te instanceof TileTreeHarvester)) { return; }

		float xRand, zRand;
		float offset = 0.65F;
		final TileTreeHarvester tile = (TileTreeHarvester) te;
		if (tile.isPowered()) { return; }
		final int dir = tile.getFrontSide();

		float xParticle = (float)x + 0.5F;
		float yParticle = (float)y + 1.0F;
		float zParticle = (float)z + 0.5F;

		//final ItemStack target = tile.getTargetStack();
		//if (target != null)
		if (tile.isChopping())
		{
			final ItemStack target = tile.getTargetStack();
			yParticle -= 0.5;
			if (dir == 4) { xParticle -= offset; }
			else if (dir == 5) { xParticle += offset; }
			else if (dir == 2) { zParticle -= offset; }
			else if (dir == 3) { zParticle += offset; }
			String particle = "blockcrack_" + Block.getIdFromBlock(Block.getBlockFromItem(target.getItem())) + "_" + target.getItemDamage();

			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle(particle, (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);

			xRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			zRand = (random.nextFloat() * 0.6F - 0.3F) * (random.nextBoolean() ? 1 : -1);
			world.spawnParticle(particle, (double)(xParticle + xRand), (double)yParticle, (double)(zParticle + zRand), 0.0D, 0.0D, 0.0D);
		}
	}
}
