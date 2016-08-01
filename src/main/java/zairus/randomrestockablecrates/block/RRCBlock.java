package zairus.randomrestockablecrates.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RRCBlock extends Block
{
	private String modName;
	
	protected RRCBlock(Material material)
	{
		super(material);
	}
	
	public RRCBlock setUnlocalizedName(String unlocalizedName)
	{
		this.modName = unlocalizedName;
		super.setUnlocalizedName(unlocalizedName);
		return this;
	}
	
	public String getModName()
	{
		return this.modName;
	}
}
