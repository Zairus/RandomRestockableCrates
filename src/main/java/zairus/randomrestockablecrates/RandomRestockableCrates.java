package zairus.randomrestockablecrates;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zairus.randomrestockablecrates.proxy.CommonProxy;

@Mod(modid = RRCConstants.MODID, name = RRCConstants.NAME, version = RRCConstants.VERSION)
public class RandomRestockableCrates
{
	public static Logger logger;
	
	@SidedProxy(clientSide = RRCConstants.MOD_CLIENT_PROXY, serverSide = RRCConstants.MOD_COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance(RRCConstants.MODID)
	public static RandomRestockableCrates instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		RandomRestockableCrates.proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RandomRestockableCrates.proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		RandomRestockableCrates.proxy.postInit(event);
	}
}
