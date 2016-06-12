package zairus.randomrestockablecrates.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.randomrestockablecrates.RandomRestockableCrates;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class RRCBlocks
{
	public static final Block crate;
	
	static
	{
		crate = new BlockCrate().setUnlocalizedName("BlockCrate");
	}
	
	public static void init()
	{
		RandomRestockableCrates.proxy.registerBlock(crate, ((BlockCrate)crate).getModName());
		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
	}
	
	public static void initModels()
	{
		RandomRestockableCrates.proxy.registerBlockModel(crate, 0, ((BlockCrate)crate).getModName() + "1");
		RandomRestockableCrates.proxy.registerBlockModel(crate, 1, ((BlockCrate)crate).getModName() + "2");
		RandomRestockableCrates.proxy.registerBlockModel(crate, 2, ((BlockCrate)crate).getModName() + "3");
		RandomRestockableCrates.proxy.registerBlockModel(crate, 3, ((BlockCrate)crate).getModName() + "4");
	}
}
