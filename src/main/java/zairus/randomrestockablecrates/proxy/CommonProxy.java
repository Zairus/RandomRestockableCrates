package zairus.randomrestockablecrates.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		GameRegistry.registerItem(item, name);
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
	
	public void registerBlock(Block block, Class <? extends ItemBlock> clazz, boolean model, String modName)
	{
		GameRegistry.registerBlock(block, clazz, modName);
	}
	
	public void registerBlockModel(Block block, int metadata, String modName)
	{
	}
}
