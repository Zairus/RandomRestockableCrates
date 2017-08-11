package zairus.randomrestockablecrates.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import zairus.randomrestockablecrates.RRCConstants;

public class RRCItem extends Item
{
	public static final String REWARD_CASE_ID = "reward_case";
	
	public RRCItem()
	{
		super();
	}
	
	public RRCItem(String id)
	{
		this();
		this.setRegistryName(new ResourceLocation(RRCConstants.MODID, id));
		this.setUnlocalizedName(id);
	}
	
	public RRCItem(String id, CreativeTabs creativeTab)
	{
		this(id);
		this.setCreativeTab(creativeTab);
	}
}
