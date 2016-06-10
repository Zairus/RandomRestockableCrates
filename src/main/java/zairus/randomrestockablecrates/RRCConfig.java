package zairus.randomrestockablecrates;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class RRCConfig
{
	public static Configuration config;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		
		config.load();
		
		config.save();
	}
}
