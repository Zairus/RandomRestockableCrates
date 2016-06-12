package zairus.randomrestockablecrates.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.randomrestockablecrates.RRCConstants;
import zairus.randomrestockablecrates.block.RRCBlock;

public class ClientProxy extends CommonProxy
{
	public static final Minecraft mc = Minecraft.getMinecraft();
	
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
		mc.getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(itemId, "inventory"));
	}
	
	@Override
	public void registerItemModel(Item item, int meta, String texture)
	{
		ModelBakery.registerItemVariants(item, new ResourceLocation(RRCConstants.MODID, texture));
		
		String itemId = RRCConstants.MODID + ":" + texture;
		mc.getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(itemId, "inventory"));
	}
	
	public void registerBlockModel(RRCBlock block, String modName)
	{
		registerBlockModel(block, 0, modName);
	}
	
	@Override
	public void registerBlockModel(Block block, int meta, String modName)
	{
		Item item = Item.getItemFromBlock(block);
		
		if (item != null)
		{
			registerItemModel(item, meta, modName);
		}
	}
}
