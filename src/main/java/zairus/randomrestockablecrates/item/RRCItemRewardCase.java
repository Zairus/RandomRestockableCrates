package zairus.randomrestockablecrates.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import zairus.randomrestockablecrates.RRCConfig;
import zairus.randomrestockablecrates.sound.RRCSoundEvents;
import zairus.randomrestockablecrates.util.RRCUtils;

public class RRCItemRewardCase extends RRCItem
{
	public RRCItemRewardCase (String id, CreativeTabs creativeTab)
	{
		super(id, creativeTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
	{
		world.playSound((EntityPlayer)null, player.getPosition(), RRCSoundEvents.REWARD_CASE, SoundCategory.PLAYERS, 1.0F, 1.2F / (world.rand.nextFloat() * 0.2f + 0.9f));
		
		for (int i = 0; i < 3; ++i)
		{
			ItemStack loot = RRCUtils.getStackFromPool(RRCConfig.rewardCaseLoot, itemRand);
			
			if (loot != null)
			{
				player.dropItem(loot, false, true);
				
				for (int j = 0; j < 5; ++j)
					world.spawnParticle(
							EnumParticleTypes.CLOUD
							, player.posX
							, player.posY
							, player.posZ
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, ((double)(1 - itemRand.nextInt(3))) * 0.6D
							, new int[] { });
			}
		}
		
		--itemStack.stackSize;
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
	}
}
