package zairus.randomrestockablecrates.block;

import net.minecraft.block.material.Material;

public class BlockCrate extends RRCBlockFacingHorizontal
{
	public BlockCrate()
	{
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setResistance(0.7F);
		this.setHardness(1.9F);
		this.setHarvestLevel("axe", 0);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.87F, 0.87F, 0.87F);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
