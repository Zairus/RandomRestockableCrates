package zairus.randomrestockablecrates.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RRCEventHandler
{
	public static int restockTicks = 0;
	
	@SubscribeEvent
	public void worldTickEvent(TickEvent.WorldTickEvent event)
	{
		++restockTicks;
	}
}
