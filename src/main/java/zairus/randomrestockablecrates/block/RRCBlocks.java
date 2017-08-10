package zairus.randomrestockablecrates.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.randomrestockablecrates.RRCConstants;
import zairus.randomrestockablecrates.RandomRestockableCrates;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class RRCBlocks
{
	public static final Block CRATE;
	public static final Block CRATE2;
	public static final Block CRATE3;
	public static final Block CRATE4;
	
	static
	{
		CRATE = new BlockCrate(Material.WOOD, 0).setSoundType(SoundType.WOOD).setRegistryName(new ResourceLocation(RRCConstants.MODID, "blockcrate1")).setUnlocalizedName("blockcrate1");
		CRATE2 = new BlockCrate(Material.WOOD, 1).setSoundType(SoundType.WOOD).setRegistryName(new ResourceLocation(RRCConstants.MODID, "blockcrate2")).setUnlocalizedName("blockcrate2");
		CRATE3 = new BlockCrate(Material.ANVIL, 2).setSoundType(SoundType.METAL).setRegistryName(new ResourceLocation(RRCConstants.MODID, "blockcrate3")).setUnlocalizedName("blockcrate3");
		CRATE4 = new BlockCrate(Material.ANVIL, 3).setSoundType(SoundType.METAL).setRegistryName(new ResourceLocation(RRCConstants.MODID, "blockcrate4")).setUnlocalizedName("blockcrate4");
	}
	
	public static void init()
	{
		RandomRestockableCrates.proxy.registerBlock(CRATE, ((BlockCrate)CRATE).getModName());
		RandomRestockableCrates.proxy.registerBlock(CRATE2, ((BlockCrate)CRATE2).getModName());
		RandomRestockableCrates.proxy.registerBlock(CRATE3, ((BlockCrate)CRATE3).getModName());
		RandomRestockableCrates.proxy.registerBlock(CRATE4, ((BlockCrate)CRATE4).getModName());
		
		GameRegistry.registerTileEntity(TileEntityCrate.class, "tileEntityCrate");
		RandomRestockableCrates.proxy.initTESR();
	}
	
	public static void initModels()
	{
		RandomRestockableCrates.proxy.registerBlockModel(CRATE, 0, ((BlockCrate)CRATE).getModName());
		RandomRestockableCrates.proxy.registerBlockModel(CRATE2, 0, ((BlockCrate)CRATE2).getModName());
		RandomRestockableCrates.proxy.registerBlockModel(CRATE3, 0, ((BlockCrate)CRATE3).getModName());
		RandomRestockableCrates.proxy.registerBlockModel(CRATE4, 0, ((BlockCrate)CRATE4).getModName());
	}
}
