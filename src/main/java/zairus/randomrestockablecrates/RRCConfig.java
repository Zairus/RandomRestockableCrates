package zairus.randomrestockablecrates;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;

public class RRCConfig
{
	public static Configuration config;
	
	public static int tier1RestockTime = 6000;
	public static int tier2RestockTime = 24000;
	public static int tier3RestockTime = 24000;
	public static int tier4RestockTime = 24000;
	
	public static NBTTagList crateTier1 = new NBTTagList();
	public static NBTTagList crateTier2 = new NBTTagList();
	public static NBTTagList crateTier3 = new NBTTagList();
	public static NBTTagList crateTier4 = new NBTTagList();
	
	public static void init(File file)
	{
		config = new Configuration(file);
		
		config.load();
		
		config.setCategoryComment("CRATE_RESTOCK_TIMES", "Restock time in ticks, 24000 ticks is one Minecraft day.");
		
		tier1RestockTime = config.getInt("tier1RestockTime", "CRATE_RESTOCK_TIMES", tier1RestockTime, 10, 300000, "Ticks for tier 1 crate.");
		tier2RestockTime = config.getInt("tier2RestockTime", "CRATE_RESTOCK_TIMES", tier2RestockTime, 10, 300000, "Ticks for tier 2 crate.");
		tier3RestockTime = config.getInt("tier3RestockTime", "CRATE_RESTOCK_TIMES", tier3RestockTime, 10, 300000, "Ticks for tier 3 crate.");
		tier4RestockTime = config.getInt("tier4RestockTime", "CRATE_RESTOCK_TIMES", tier4RestockTime, 10, 300000, "Ticks for tier 4 crate.");
		
		config.setCategoryComment("TIER1_POOL", "Pool configuration for tier 1 crates.");
		
		int tier1Elements = config.getInt("totalElements", "TIER1_POOL", 4, 1, 1000, "Total number of elements(items) on this pool.");
		
		for (int i = 0; i < tier1Elements; ++i)
		{
			int weight = config.getInt("weight_item" + i, "TIER1_POOL", 1, 1, 20, "Weight for generation on crate.");
			String itemId = config.getString("itemId_item" + i, "TIER1_POOL", "minecraft:stone_sword", "Named item id.");
			int min = config.getInt("min_item" + i, "TIER1_POOL", 0, 0, 64, "Minimum count for the item stack.");
			int max = config.getInt("max_item" + i, "TIER1_POOL", 1, 1, 64, "Maximum count for the item stack.");
			int meta = config.getInt("meta_item" + i, "TIER1_POOL", 0, 0, 64, "Metadata for the item.");
			String nbt = config.getString("nbt_item" + i, "TIER1_POOL", "", "Additional NBT Data for the item.");
			
			NBTTagCompound item = new NBTTagCompound();
			item.setInteger("weight", weight);
			item.setString("itemId", itemId);
			item.setInteger("min", min);
			item.setInteger("max", max);
			item.setInteger("meta", meta);
			item.setString("NBTData", nbt);
			crateTier1.appendTag(item);
		}
		/*
		crateTier1 = tier1Default();
		NBTTagCompound tier1 = new NBTTagCompound();
		tier1.setTag("tier1", crateTier1);
		String tier1String = config.getString("Tier1Pool", "CRATE_TIERS", tier1.toString(), "Defines the item pool to choose from for crater tiers");
		
		try {
			crateTier1 = JsonToNBT.getTagFromJson(tier1String).getTagList("tier1", Constants.NBT.TAG_COMPOUND);
		} catch (NBTException e) {
		}
		*/
		config.save();
	}
	/*
	public static NBTTagList tier1Default()
	{
		NBTTagList tier1Default = new NBTTagList();
		NBTTagCompound item;
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:leather_helmet");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:leather_boots");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:leather_chestplate");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:stone_sword");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:wooden_sword");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:potion");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 5);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:experience_bottle");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:arrow");
		item.setInteger("min", 0);
		item.setInteger("max", 4);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:string");
		item.setInteger("min", 0);
		item.setInteger("max", 6);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:stick");
		item.setInteger("min", 0);
		item.setInteger("max", 6);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "minecraft:enchanted_book");
		item.setInteger("min", 0);
		item.setInteger("max", 1);
		item.setInteger("meta", 0);
		item.setString("NBTData", "{StoredEnchantments:[{id:16,lvl:1}]}");
		tier1Default.appendTag(item);
		
		item = new NBTTagCompound();
		item.setInteger("weight", 1);
		item.setString("itemId", "weaponcaseloot:weaponcase1");
		item.setInteger("min", 0);
		item.setInteger("max", 2);
		item.setInteger("meta", 0);
		item.setString("NBTData", "");
		tier1Default.appendTag(item);
		
		return tier1Default;
	}
	*/
}
