package zairus.randomrestockablecrates.proxy;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.randomrestockablecrates.RRCConstants;
import zairus.randomrestockablecrates.block.RRCBlock;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		;
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		;
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		;
	}
	
	@Override
	public void registerItem(Item item, String name)
	{
		super.registerItem(item, name);
	}
	
	@Override
	public void registerItemModel(Item item, int meta)
	{
		String itemId = RRCConstants.MODID + ":"; // + item.getModName();
		CommonProxy.mc.getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(itemId, "inventory"));
	}
	
	@Override
	public void registerItemModel(Item item, int meta, String texture)
	{
		String itemId = RRCConstants.MODID + ":" + texture;
		CommonProxy.mc.getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(itemId, "inventory"));
	}
	
	public void registerBlockModel(RRCBlock block)
	{
		registerBlockModel(block, 0);
	}
	
	@Override
	public void registerBlockModel(RRCBlock block, int meta)
	{
		Item item = Item.getItemFromBlock(block);
		
		if (item != null)
		{
			registerItemModel(item, meta, block.getModName());
		}
	}
}
