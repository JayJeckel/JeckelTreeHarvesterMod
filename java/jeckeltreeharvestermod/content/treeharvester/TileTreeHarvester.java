package jeckeltreeharvestermod.content.treeharvester;

import java.util.ArrayList;
import java.util.List;

import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.api.tiles.ITileProcessor;
import jeckelcorelibrary.api.tiles.ITileTanker;
import jeckelcorelibrary.base.tiles.ATileInventory;
import jeckelcorelibrary.core.BlockPosition;
import jeckelcorelibrary.core.fluids.BasicFluidTank;
import jeckelcorelibrary.core.processes.basic.TickProcess;
import jeckelcorelibrary.core.processes.special.TankExchangeProcess;
import jeckelcorelibrary.utils.DirUtil;
import jeckeltreeharvestermod.content.ContentManager;
import jeckeltreeharvestermod.content.treeharvester.client.ScreenTreeHarvester;
import jeckeltreeharvestermod.core.Refs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileTreeHarvester
extends ATileInventory
implements ITileInteractable, ITileGuiActivator, IFluidHandler, ITileProcessor, ITileTanker
{
	public static boolean isLog(Block block, int meta)
	{
		ItemStack stack = new ItemStack(block, 1, meta);

		ArrayList<ItemStack> targets = OreDictionary.getOres("logWood");
		for (int index = 0; index < targets.size(); index++)
		{
			if (OreDictionary.itemMatches(targets.get(index), stack, false))
			{
				return true;
			}
		}
		return false;
	}

	public TileTreeHarvester() { this(false); }

	public TileTreeHarvester(boolean dummy)
	{
		super(8, dummy);
		this.setTileName(ContentManager.ModBlocks.tree_harvester.getUnlocalizedName() + ".name");

		this._processes = new ArrayList<ITickProcess>();
		this._tanks = new ArrayList<FluidTank>();

		this._tank = new BasicFluidTank(1500);
		this._tanks.add(this._tank);
		this.tankExchanger = new TankExchangeProcess("fluid_transfer", 20, false, this, this.getTank(), 0, 1);
		this._processes.add(this.tankExchanger);
		this.treeChopperTick = new TickProcess("tree_chopper", 100);
		this._processes.add(this.treeChopperTick);

		this.outputIndexArray = new int[] { 2, 3, 4, 5, 6, 7 };
	}

	private final List<ITickProcess> _processes;

	private final List<FluidTank> _tanks;

	public final ITickProcess tankExchanger;
	public final ITickProcess treeChopperTick;

	public final int[] outputIndexArray;

	public BlockPosition getBlockPosition() { return new BlockPosition(this.xCoord, this.yCoord, this.zCoord); }

	protected ItemStack getSlotStack(int index) { return this.getStackInSlot(index); }

	protected void setSlotStack(int index, ItemStack stack) { this.setInventorySlotContents(index, stack); }

	public int getOutputIndex(ItemStack match)
	{
		if (match == null) { return -1; }
		int empty = -1;
		for (int index : this.outputIndexArray)
		{
			ItemStack stack = this.getSlotStack(index);
			if (stack == null && empty == -1) { empty = index; }
			if (stack != null && stack.isItemEqual(match))
			{
				int size = stack.stackSize + match.stackSize;
				if (size <= stack.getMaxStackSize() && size <= this.getInventoryStackLimit())
				{
					return index;
				}
			}
		}
		return empty;
	}

	public ForgeDirection[][] getSteps(final ForgeDirection frontSide)
	{
		final ForgeDirection dirBackward = frontSide;
		final ForgeDirection dirForward = DirUtil.reverse(dirBackward);//dirBackward.getOpposite();
		final ForgeDirection dirLeft = DirUtil.left(dirForward);//dirForward.getRotation(ForgeDirection.DOWN);
		final ForgeDirection dirRight = DirUtil.right(dirForward);//dirLeft.getOpposite();

		ForgeDirection[][] steps = new ForgeDirection[][]
		{
			{ dirForward, dirForward, dirForward, dirForward, dirLeft, dirLeft },
			{ dirForward, dirForward, dirForward, dirForward, dirRight, dirRight },
			{ dirForward, dirForward, dirForward, dirForward, dirLeft },
			{ dirForward, dirForward, dirForward, dirForward, dirRight },
			{ dirForward, dirForward, dirForward, dirForward },

			{ dirForward, dirForward, dirForward, dirLeft, dirLeft },
			{ dirForward, dirForward, dirForward, dirRight, dirRight },
			{ dirForward, dirForward, dirForward, dirLeft },
			{ dirForward, dirForward, dirForward, dirRight },
			{ dirForward, dirForward, dirForward },

			{ dirForward, dirForward, dirLeft, dirLeft },
			{ dirForward, dirForward, dirRight, dirRight },
			{ dirForward, dirForward, dirLeft },
			{ dirForward, dirForward, dirRight },
			{ dirForward, dirForward },

			{ dirForward, dirLeft, dirLeft },
			{ dirForward, dirRight, dirRight },
			{ dirForward, dirLeft },
			{ dirForward, dirRight },
			{ dirForward },

			{ dirLeft, dirLeft },
			{ dirRight, dirRight },
			{ dirLeft },
			{ dirRight },
			{ }
		};

		return steps;
	}

	public BlockPosition getRootPosition(final ForgeDirection frontSide)
	{
		return this.getRootPosition(frontSide, this.yCoord);
	}

	public BlockPosition getRootPosition(final ForgeDirection frontSide, final int y)
	{
		final ForgeDirection dirForward = DirUtil.reverse(frontSide);
		return new BlockPosition(this.xCoord + dirForward.offsetX, y, this.zCoord + dirForward.offsetZ);
	}

	public ItemStack[][] getTargetGrid(final int height)
	{
		final ForgeDirection frontSide = ForgeDirection.getOrientation(this.getFrontSide());
		return this.getTargetGrid(frontSide, height);
	}

	public BlockPosition[][] getPositionGrid(final int height)
	{
		final ForgeDirection frontSide = ForgeDirection.getOrientation(this.getFrontSide());
		return this.getPositionGrid(frontSide, height);
	}

	public BlockPosition[][] getPositionGrid(final ForgeDirection frontSide, final int height)
	{
		final ForgeDirection[][] steps = this.getSteps(frontSide);
		final BlockPosition root = this.getRootPosition(frontSide);
		final int y = root.y + height;

		final BlockPosition[][] positions = new BlockPosition[5][5];
		for (int indexStep = 0; indexStep < steps.length; indexStep++)
		{
			ForgeDirection[] step = steps[indexStep];
			int x = root.x;
			int z = root.z;
			for (int index = 0; index < step.length; index++)
			{
				ForgeDirection dir = step[index];
				x += dir.offsetX;
				z += dir.offsetZ;
			}

			final int columns = 5;
			//final int rows = 5;
			final int column = indexStep % columns;
			final int row = (indexStep - column) / columns;

			positions[column][row] = new BlockPosition(x, y, z);//(!isLog(block, meta) ? null : new ItemStack(block, 1, meta & 3));
		}
		return positions;
	}

	public ItemStack[][] getTargetGrid(final ForgeDirection frontSide, final int height)
	{
		final ForgeDirection[][] steps = this.getSteps(frontSide);
		final BlockPosition root = this.getRootPosition(frontSide);
		final int y = root.y + height;

		final ItemStack[][] stacks = new ItemStack[5][5];
		for (int indexStep = 0; indexStep < steps.length; indexStep++)
		{
			ForgeDirection[] step = steps[indexStep];
			int x = root.x;
			int z = root.z;
			for (int index = 0; index < step.length; index++)
			{
				ForgeDirection dir = step[index];
				x += dir.offsetX;
				z += dir.offsetZ;
			}

			final int columns = 5;
			//final int rows = 5;
			int column = indexStep % columns;
			int row = (indexStep - column) / columns;

			Block block = this.worldObj.getBlock(x, y, z);
			int meta = this.worldObj.getBlockMetadata(x, y, z);
			stacks[column][row] = (!isLog(block, meta) ? null : new ItemStack(block, 1, meta & 3));
		}
		return stacks;
	}

	public BlockPosition refreshTargetPosition(final int height)
	{
		final ForgeDirection frontSide = ForgeDirection.getOrientation(this.getFrontSide());
		final ForgeDirection[][] steps = this.getSteps(frontSide);
		final BlockPosition root = this.getRootPosition(frontSide);

		int yTarget = -1;
		int xTarget = 0;
		int zTarget = 0;
		for (int indexStep = 0; indexStep < steps.length; indexStep++)
		{
			ForgeDirection[] step = steps[indexStep];
			int x = root.x;
			int z = root.z;
			for (int index = 0; index < step.length; index++)
			{
				ForgeDirection dir = step[index];
				x += dir.offsetX;
				z += dir.offsetZ;
			}
			for (int y = root.y + height - 1; y >= root.y; y--)
			{
				Block target = this.worldObj.getBlock(x, y, z);
				int meta = this.worldObj.getBlockMetadata(x, y, z);
				if (isLog(target, meta))
				{
					xTarget = x;
					yTarget = y;
					zTarget = z;
					break;
				}
			}
			if (yTarget > -1) { break; }
		}
		if (yTarget > -1) { return new BlockPosition(xTarget, yTarget, zTarget); }
		return null;
	}

	public ItemStack getTargetStack()
	{
		if (this._targetLoc == null) { return null; }
		final Block block = this.worldObj.getBlock(this._targetLoc.x, this._targetLoc.y, this._targetLoc.z);
		final int meta = this.worldObj.getBlockMetadata(this._targetLoc.x, this._targetLoc.y, this._targetLoc.z);
		return new ItemStack(block, 1, meta & 3);
	}

	public BlockPosition getTargetPosition() { return this._targetLoc; }
	public BlockPosition _targetLoc = null;

	public boolean isPowered() { return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord); }

	public int getChoppingStatus()
	{
		final ItemStack targetStack = this.getTargetStack();
		if (targetStack == null) { return 0; }
		final int outputIndex = this.getOutputIndex(targetStack);
		if (outputIndex < 0) { return 1; }
		if (this._tank.getFluidAmount() < this._cost) { return 1; }
		if (this.isPowered()) { return 1; }
		return 2;
	}

	private final int _cost = 1;
	public boolean isChopping()
	{
		if (this._tank.getFluidAmount() < this._cost) { return false; }
		if (this.isPowered()) { return false; }

		final ItemStack targetStack = this.getTargetStack();
		if (targetStack == null) { return false; }
		final int outputIndex = this.getOutputIndex(targetStack);
		if (outputIndex < 0) { return false; }
		return true;
	}

	@Override public void updateEntity()
	{
		boolean dirty = false;

		final int cost = 1;
		final int height = 10;

		final BlockPosition loc = this.refreshTargetPosition(height);
		this._targetLoc = loc;
		final ItemStack resultStack = this.getTargetStack();
		if (!this.worldObj.isRemote)
		{
			if (this.tankExchanger.updateProcess(this.worldObj)) { dirty = true; }

			boolean clearCycle = true;
			final int outputIndex = this.getOutputIndex(resultStack);
			final boolean chopping = this.isChopping();
			if (chopping)
			{
				clearCycle = false;
				if (this.treeChopperTick.updateProcess(this.worldObj)) { dirty = true; }
				if (this.treeChopperTick.isCycleEnd())
				{
					this._tank.drain(cost);
					this.worldObj.setBlockToAir(this._targetLoc.x, this._targetLoc.y, this._targetLoc.z);

					final ItemStack outputStack = this.getSlotStack(outputIndex);
					if (outputStack != null) { resultStack.stackSize += outputStack.stackSize; }
					this.setSlotStack(outputIndex, resultStack);
				}
			}
			if (clearCycle) { this.treeChopperTick.cancelCycle(this.worldObj); }

			if (chopping != this.isChopping())
			{
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (dirty) { this.markDirty(); }
	}


	// ##################################################
	//
	// Tank Capacity
	//
	// ##################################################

	private BasicFluidTank _tank = null;

	public BasicFluidTank getTank() { return this._tank; }

	public FluidStack getTankFluid() { return this._tank.getFluid(); }


	// ##################################################
	//
	// ITileInteractable
	//
	// ##################################################

	@Override public void interact(EntityPlayer player, World world, int x, int y, int z, int side)
	{
		if (!player.isSneaking()) {  player.openGui(Refs.getMod(), 0, world, x, y, z); }
	}


	// ##################################################
	//
	// ITileGuiActivator
	//
	// ##################################################

	@Override public Object createContainer(EntityPlayer player) { return new ContainerTreeHarvester(player, this); }

	@Override public Object createScreen(EntityPlayer player) { return new ScreenTreeHarvester(player, this); }


	// ##################################################
	//
	// ITileProcessor
	//
	// ##################################################

	@Override public List<ITickProcess> getProcesses() { return this._processes; }


	// ##################################################
	//
	// ITileTanker
	//
	// ##################################################

	@Override public List<FluidTank> getTanks() { return this._tanks; }


	// ##################################################
	//
	// IFluidHandler
	//
	// ##################################################

	@Override public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		int amountFilled = this._tank.fill(resource, doFill);
		if (doFill && amountFilled > 0) { this.markDirty(); }
		return amountFilled;
	}

	@Override public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		FluidStack drained = this._tank.drain(resource.amount, doDrain);
		if (doDrain && drained != null) { this.markDirty(); }
		return drained;
	}

	@Override public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		FluidStack drained = this._tank.drain(maxDrain, doDrain);
		if (doDrain && drained != null) { this.markDirty(); }
		return drained;
	}

	@Override public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[] { this._tank.getInfo() };
	}
}
