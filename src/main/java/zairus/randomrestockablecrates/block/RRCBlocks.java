package zairus.randomrestockablecrates.block;

import zairus.randomrestockablecrates.RandomRestockableCrates;

public class RRCBlocks
{
	public static final RRCBlock crate;
	
	static
	{
		crate = new BlockCrate().setUnlocalizedName("BlockCrate");
	}
	
	public static void init()
	{
		RandomRestockableCrates.proxy.registerBlock(crate);
	}
	
	public static void initModels()
	{
		RandomRestockableCrates.proxy.registerBlockModel(crate, 0);
	}
}
