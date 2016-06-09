package zairus.randomrestockablecrates.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zairus.randomrestockablecrates.block.RRCBlock;

public class CommonProxy
{
	public static final Minecraft mc = Minecraft.getMinecraft();
	
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
	
	public void registerBlock(RRCBlock block)
	{
		registerBlock(block, false);
	}
	
	public void registerBlock(RRCBlock block, boolean model)
	{
		registerBlock(block, ItemBlock.class, model);
	}
	
	public void registerBlock(RRCBlock block, Class <? extends ItemBlock> clazz, boolean model)
	{
		GameRegistry.registerBlock(block, clazz, block.getModName());
	}
	
	public void registerBlockModel(RRCBlock block, int metadata)
	{
	}
}
