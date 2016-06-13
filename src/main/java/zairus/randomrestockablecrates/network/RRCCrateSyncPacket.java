package zairus.randomrestockablecrates.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class RRCCrateSyncPacket extends AbstractPacket
{
	private int x;
	private int y;
	private int z;
	private int ticks;
	private int lastOpened;
	
	public RRCCrateSyncPacket()
	{
		;
	}
	
	public RRCCrateSyncPacket(int x, int y, int z, int ticks, int lastOpened)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.ticks = ticks;
		this.lastOpened = lastOpened;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(ticks);
		buffer.writeInt(lastOpened);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		ticks = buffer.readInt();
		lastOpened = buffer.readInt();
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
		TileEntity tileEntity = player.worldObj.getTileEntity(new BlockPos(x, y, z));
		
		if (tileEntity instanceof TileEntityCrate)
		{
			TileEntityCrate crate = (TileEntityCrate)tileEntity;
			crate.syncValues(ticks, lastOpened);
		}
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		;
	}
}
