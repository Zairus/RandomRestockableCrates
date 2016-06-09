package zairus.randomrestockablecrates.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class RRCBlock extends Block
{
	private String modName;
	
	protected RRCBlock(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabs.tabDecorations);
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
