package zairus.randomrestockablecrates.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.randomrestockablecrates.RRCConstants;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	public void init(FMLInitializationEvent e)
	{
		;
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	public void registerItem(Item item, String name)
	{
		GameRegistry.register(item);
	}
	
	public void registerItemModel(Item item, int meta)
	{
	}
	
	public void registerItemModel(Item item, int meta, String texture)
	{
	}
	
	public void registerBlock(Block block, String modName)
	{
		registerBlock(block, false, modName);
	}
	
	public void registerBlock(Block block, boolean model, String modName)
	{
		registerBlock(block, ItemBlock.class, model, modName);
	}
	
	public void registerBlock(Block block, Class <? extends ItemBlock> clazz, boolean model, String name)
	{
		GameRegistry.register(block);
		registerItem(new ItemBlock(block).setRegistryName(new ResourceLocation(RRCConstants.MODID, name)), name);
	}
	
	public void registerBlockModel(Block block, int metadata, String modName)
	{
	}
	
	public void initTESR()
	{
	}
}
