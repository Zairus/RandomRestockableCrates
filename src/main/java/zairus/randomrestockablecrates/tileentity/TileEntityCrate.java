package zairus.randomrestockablecrates.tileentity;

import java.util.Random;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zairus.randomrestockablecrates.RRCConfig;
import zairus.randomrestockablecrates.inventory.ContainerCrate;
import zairus.randomrestockablecrates.sound.RRCSoundEvents;

public class TileEntityCrate extends TileEntityLockable implements ITickable, IInventory
{
	public int playersUsing;
	
	private ItemStack[] chestContents = new ItemStack[9];
	private String customName;
	private int worldTicks;
	private int lastOpened;
	private boolean open = false;
	private boolean firstTime = true;
	
	private int tier = -1;
	private static final NBTTagList[] tierPools = new NBTTagList[] {RRCConfig.crateTier1, RRCConfig.crateTier2, RRCConfig.crateTier3, RRCConfig.crateTier4};
	
	public TileEntityCrate()
	{
		this.tier = -1;
	}
	
	public TileEntityCrate(int crateTier)
	{
		this.tier = crateTier;
	}
	
	public String getDefaultName()
	{
		return "Crate";
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName()? this.customName : "container.crate";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return customName != null;
	}
	
	public void setCustomName(String name)
	{
		this.customName = name;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		return new ContainerCrate(playerInventory, this, player);
	}
	
	@Override
	public String getGuiID()
	{
		return "randomrestockablecrates:crate";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.chestContents[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.chestContents[index] != null && this.chestContents[index] != ItemStack.EMPTY)
		{
			if (this.chestContents[index].getCount() <= count)
			{
				ItemStack itemstack1 = this.chestContents[index];
                this.chestContents[index] = ItemStack.EMPTY;
                this.markDirty();
                return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.chestContents[index].splitStack(count);
				
                if (this.chestContents[index].getCount() == 0)
                {
                    this.chestContents[index] = ItemStack.EMPTY;
                }
                
                this.markDirty();
                return itemstack;
			}
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.chestContents[index] != null && !this.chestContents[index].isEmpty())
        {
            ItemStack itemstack = this.chestContents[index];
            this.chestContents[index] = ItemStack.EMPTY;
            return itemstack;
        }
        else
        {
            return ItemStack.EMPTY;
        }
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents[index] = stack;
		
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
        
        this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (this.playersUsing < 0)
			{
				this.playersUsing = 0;
			}
			
			++this.playersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
		
		if (!this.open)
			this.lastOpened = this.worldTicks;
		
		this.world.playSound((EntityPlayer)null, pos, RRCSoundEvents.CRATE_OPEN, SoundCategory.BLOCKS, 1.0F, 1.2F / (this.world.rand.nextFloat() * 0.2f + 0.9f));
		this.open = true;
		
		updateMe();
	}
	
	public int getTier()
	{
		return this.tier;
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return super.shouldRefresh(world, pos, oldState, newState);
	}
	
	@Override
	public void update()
	{
		if (this.tier > -1)
		{
			++this.worldTicks;
			
			int ticksEllapsed = this.worldTicks - this.lastOpened;
			
			int restockTime = 0;
			
			switch(this.tier)
			{
			case 1:
				restockTime = RRCConfig.tier2RestockTime;
				break;
			case 2:
				restockTime = RRCConfig.tier3RestockTime;
				break;
			case 3:
				restockTime = RRCConfig.tier4RestockTime;
				break;
			default:
				restockTime = RRCConfig.tier1RestockTime;
				break;
			}
			
			if (ticksEllapsed >= restockTime || this.firstTime)
			{
				this.firstTime = false;
				this.lastOpened = worldTicks;
				
				this.world.playSound((EntityPlayer)null, pos, RRCSoundEvents.CRATE_OPEN, SoundCategory.BLOCKS, 1.0F, 1.2F / (this.world.rand.nextFloat() * 0.2f + 0.9f));
				
				restock(this.world.rand);
			}
			
			updateMe();
		}
	}
	
	private void restock(Random rand)
	{
		if (this.tier < 0)
			return;
		
		boolean addedItem = false;
		
		this.open = false;
		
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = ItemStack.EMPTY;
			if (rand.nextInt(6) == 0)
			{
				this.chestContents[i] = getStackFromPool(tierPools[tier], rand);
				
				if (this.chestContents[i] != null && !this.chestContents[i].isEmpty())
					addedItem = true;
			}
		}
		
		if (!addedItem)
		{
			this.chestContents[rand.nextInt(this.chestContents.length)] = getStackFromPool(tierPools[tier], rand);
		}
		
		this.markDirty();
	}
	
	public void syncValues(int ticks, int lastOpened, boolean open)
	{
		this.worldTicks = ticks;
		this.lastOpened = lastOpened;
		this.open = open;
	}
	
	public boolean getIsOpen()
	{
		return this.open;
	}
	
	private ItemStack getStackFromPool(NBTTagList list, Random rand)
	{
		ItemStack stack = ItemStack.EMPTY;
		
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
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator() && this.getBlockType() instanceof BlockChest)
		{
			--this.playersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), true);
			this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), true);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];
		
		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			
			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = new ItemStack(nbttagcompound);
			}
		}
		
		this.firstTime = compound.getBoolean("first");
		this.worldTicks = compound.getInteger("ticks");
		this.lastOpened = compound.getInteger("lastOpened");
		this.open = compound.getBoolean("open");
		this.tier = compound.getInteger("tier");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null && !this.chestContents[i].isEmpty())
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		
		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
		
		compound.setBoolean("first", this.firstTime);
		compound.setInteger("tier", this.tier);
		compound.setInteger("ticks", this.worldTicks);
		compound.setInteger("lastOpened", this.lastOpened);
		compound.setBoolean("open", this.open);
		
		return compound;
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		;
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if (id == 1)
		{
			this.playersUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tag = super.getUpdateTag();
		
		writeSyncableDataToNBT(tag);
		
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		super.handleUpdateTag(tag);
		this.readSyncableDataFromNBT(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new SPacketUpdateTileEntity(this.getPos(), 1, syncData);
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
	
	protected void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("worldTicks", this.worldTicks);
		syncData.setInteger("lastOpened", this.lastOpened);
		syncData.setBoolean("open", this.open);
		syncData.setInteger("tier", this.tier);
	}
	
	protected void readSyncableDataFromNBT(NBTTagCompound syncData)
	{
		this.worldTicks = syncData.getInteger("worldTicks");
		this.lastOpened = syncData.getInteger("lastOpened");
		this.open = syncData.getBoolean("open");
		this.tier = syncData.getInteger("tier");
	}
	
	private void updateMe()
	{
		this.markDirty();
		
		IBlockState state = this.world.getBlockState(getPos());
		this.world.notifyBlockUpdate(getPos(), state, state, 0);
	}
	
	@Override
	public boolean isEmpty()
	{
		boolean isEmpty = true;
		
		for (ItemStack stack : this.chestContents)
		{
			if (stack != null && !stack.isEmpty())
				isEmpty = false;
		}
		
		return isEmpty;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}
}
