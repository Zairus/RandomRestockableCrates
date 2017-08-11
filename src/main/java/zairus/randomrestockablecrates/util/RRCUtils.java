package zairus.randomrestockablecrates.util;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import zairus.randomrestockablecrates.RandomRestockableCrates;

public class RRCUtils
{
	public static void log(String message)
	{
		RandomRestockableCrates.log(message);
	}
	
	public static ItemStack getStackFromPool(NBTTagList list, Random rand)
	{
		ItemStack stack = null;
		
		NBTTagCompound curElement = list.getCompoundTagAt(rand.nextInt(list.tagCount()));
		
		if (curElement != null)
		{
			int amount = curElement.getInteger("max") - curElement.getInteger("min");
			amount = rand.nextInt(amount + 1) + curElement.getInteger("min");
			if (amount == 0)
				amount = 1;
			
			stack = new ItemStack(Item.getByNameOrId(curElement.getString("itemId")), amount, curElement.getInteger("meta"));
			
			NBTTagCompound tag = null;
			
			if (curElement.hasKey("NBTData") && curElement.getString("NBTData") != null && curElement.getString("NBTData").length() > 0)
			{
				try {
					tag = JsonToNBT.getTagFromJson(curElement.getString("NBTData"));
				} catch (NBTException e) {
				}
				
				if (tag != null)
					stack.setTagCompound(tag);
			}
		}
		
		return stack;
	}
}
