package zairus.randomrestockablecrates.item;

import zairus.randomrestockablecrates.RandomRestockableCrates;

public class RRCItems
{
	public static RRCItem REWARD_CASE;
	
	static
	{
		REWARD_CASE = new RRCItemRewardCase(RRCItem.REWARD_CASE_ID, RandomRestockableCrates.tabCrates);
	}
	
	public static final void register()
	{
		RandomRestockableCrates.proxy.registerItem(REWARD_CASE, RRCItem.REWARD_CASE_ID, 0, true);
	}
}
