package zairus.randomrestockablecrates.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zairus.randomrestockablecrates.RRCConstants;

public class RRCSoundEvents
{
	public static SoundEvent CRATE_OPEN;
	
	private static int lastSoundId = -1;
	
	private static int getLastID()
	{
		int lastId = 0;
		
		for (SoundEvent sound : SoundEvent.REGISTRY)
		{
			lastId = Math.max(lastId, SoundEvent.REGISTRY.getIDForObject(sound));
		}
		
		return lastId;
	}
	
	public static void setLastID()
	{
		lastSoundId = getLastID() + 1;
	}
	
	public static SoundEvent registerSound(ResourceLocation location)
	{
		SoundEvent.REGISTRY.register(lastSoundId, location, new SoundEvent(location));
		
		return SoundEvent.REGISTRY.getObjectById(lastSoundId++);
	}
	
	private static SoundEvent registerSound(String location)
	{
		return registerSound(new ResourceLocation(RRCConstants.MODID, location));
	}
	
	static
	{
		setLastID();
		
		CRATE_OPEN = registerSound("crate_open");
	}
}
