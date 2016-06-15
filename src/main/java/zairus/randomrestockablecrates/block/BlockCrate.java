package zairus.randomrestockablecrates.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import zairus.randomrestockablecrates.RandomRestockableCrates;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class BlockCrate extends BlockContainer
{
	private String modName;
	
	private final int crateTier;
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool OPEN = PropertyBool.create("open");
	
	public BlockCrate(Material material, int tier)
	{
		super(material);
		this.crateTier = tier;
		this.setCreativeTab(RandomRestockableCrates.tabCrates);
		this.setResistance(6000000.0F);
		this.setHardness(1.9F);
		this.setHarvestLevel("axe", 0);
		this.setBlockBounds(0.08F, 0.0F, 0.08F, 0.93F, 0.93F, 0.93F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false));
		
		this.setBlockUnbreakable();
	}
	
	public BlockCrate setSound(SoundType sound)
	{
		this.setStepSound(sound);
		return this;
	}
	
	public int getTier()
	{
		return this.crateTier;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			ILockableContainer ilockablecontainer = this.getLockableContainer(world, pos);
			
			if (ilockablecontainer != null)
			{
				world.playSoundAtEntity(player, "randomrestockablecrates:crate_open", 1.0f, 1.2f / (world.rand.nextFloat() * 0.2f + 0.9f));
				player.openGui(RandomRestockableCrates.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		}
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCrate(crateTier);
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(this.getLockableContainer(world, pos));
	}
	
	public ILockableContainer getLockableContainer(World world, BlockPos pos)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		
		ILockableContainer ilockablecontainer = (TileEntityCrate)tileentity;
		
		return ilockablecontainer;
	}
	
	//Facing
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {FACING, OPEN});
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		
		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}
		
		IBlockState state = getDefaultState().withProperty(FACING, enumfacing).withProperty(OPEN, false);
		
		return state;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntityCrate crate = (TileEntityCrate)world.getTileEntity(pos);
		
		boolean isOpen = crate.getIsOpen();
		
		return state.withProperty(OPEN, isOpen);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
        state = state.withProperty(FACING, enumfacing).withProperty(OPEN, false);
        world.setBlockState(pos, state, 3);
	}
	
	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer)
				.withProperty(FACING, placer.getHorizontalFacing())
				.withProperty(OPEN, false);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockDestroyedByPlayer(world, pos, state);
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	//Block
	public BlockCrate setUnlocalizedName(String unlocalizedName)
	{
		this.modName = unlocalizedName;
		super.setUnlocalizedName(unlocalizedName);
		return this;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return super.getUnlocalizedName() + "";
	}
	
	public String getModName()
	{
		return this.modName;
	}
	
	public enum EnumTier implements IStringSerializable
	{
		TIER1(0, "tier1")
		,TIER2(1, "tier2")
		,TIER3(2, "tier3")
		,TIER4(3, "tier4");
		
		private final String name;
		private final int meta;
		
		private EnumTier(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}
		
		public static EnumTier fromMeta(int meta)
		{
			switch(meta)
			{
			case 1:
				return TIER2;
			case 2:
				return TIER3;
			case 3:
				return TIER4;
			default:
				return TIER1;
			}
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
		
		public int getMetadata()
		{
			return this.meta;
		}
	}
}
