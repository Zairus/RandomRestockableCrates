package zairus.randomrestockablecrates.tileentity;

import java.util.Random;

import net.minecraft.block.BlockChest;
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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import zairus.randomrestockablecrates.RRCConfig;
import zairus.randomrestockablecrates.RandomRestockableCrates;
import zairus.randomrestockablecrates.event.RRCEventHandler;
import zairus.randomrestockablecrates.inventory.ContainerCrate;

public class TileEntityCrate extends TileEntityLockable implements ITickable, IInventory
{
	public int playersUsing;
	
	private ItemStack[] chestContents = new ItemStack[9];
	private String customName;
	private int worldTicks;
	private int lastOpened;
	private boolean open = false;
	
	private int tier;
	private static final NBTTagList[] tierPools = new NBTTagList[] {RRCConfig.crateTier1, RRCConfig.crateTier2, RRCConfig.crateTier3, RRCConfig.crateTier4};
	
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
		if (this.chestContents[index] != null)
		{
			if (this.chestContents[index].stackSize <= count)
			{
				ItemStack itemstack1 = this.chestContents[index];
                this.chestContents[index] = null;
                this.markDirty();
                return itemstack1;
			}
			else
			{
				ItemStack itemstack = this.chestContents[index].splitStack(count);
				
                if (this.chestContents[index].stackSize == 0)
                {
                    this.chestContents[index] = null;
                }
                
                this.markDirty();
                return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if (this.chestContents[index] != null)
        {
            ItemStack itemstack = this.chestContents[index];
            this.chestContents[index] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents[index] = stack;
		
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
        
        this.markDirty();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
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
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
		}
		
		if (!this.open)
			this.lastOpened = RRCEventHandler.restockTicks;
		
		this.open = true;
		
		updateMe();
	}
	
	@Override
	public void update()
	{
		this.worldTicks = RRCEventHandler.restockTicks;
		
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
		
		if (ticksEllapsed >= restockTime || this.lastOpened == 0)
		{
			this.lastOpened = worldTicks;
			
			this.worldObj.playSound(
					this.getPos().getX(), 
					this.getPos().getY(), 
					this.getPos().getZ(), 
					"randomrestockablecrates:crate_open", 
					1.0f, 1.2f / (this.worldObj.rand.nextFloat() * 0.2f + 0.9f), 
					true);
			
			restock(this.worldObj.rand);
		}
		
		updateMe();
	}
	
	private void restock(Random rand)
	{
		boolean addedItem = false;
		
		this.open = false;
		
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = null;
			if (rand.nextInt(6) == 0)
			{
				this.chestContents[i] = getStackFromPool(tierPools[tier], rand);
				
				if (this.chestContents[i] != null)
					addedItem = true;
			}
		}
		
		if (!addedItem)
		{
			this.chestContents[rand.nextInt(this.chestContents.length)] = getStackFromPool(RRCConfig.crateTier1, rand);
		}
	}
	
	public boolean getIsOpen()
	{
		return this.open;
	}
	
	private ItemStack getStackFromPool(NBTTagList list, Random rand)
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
					RandomRestockableCrates.logger.info("Erorr in stack [" + curElement.getString("NBTData") + "]:" + e.toString());
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
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.playersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
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
		
		this.tier = compound.getInteger("Tier");
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			
			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
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
		
		compound.setInteger("Tier", this.tier);
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
			this.chestContents[i] = null;
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
	public Packet<INetHandlerPlayClient> getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.getPos(), 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readSyncableDataFromNBT(pkt.getNbtCompound());
	}
	
	protected void writeSyncableDataToNBT(NBTTagCompound syncData)
	{
		syncData.setInteger("worldTicks", this.worldTicks);
		syncData.setInteger("lastOpened", this.lastOpened);
	}
	
	protected void readSyncableDataFromNBT(NBTTagCompound syncData)
	{
		this.worldTicks = syncData.getInteger("worldTicks");
		this.lastOpened = syncData.getInteger("lastOpened");
	}
	
	private void updateMe()
	{
		this.worldObj.markBlockForUpdate(this.getPos());
	}
}
