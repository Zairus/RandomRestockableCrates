package zairus.randomrestockablecrates.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class RRCCrateSyncPacket extends AbstractPacket
{
	private int x;
	private int y;
	private int z;
	private int ticks;
	private int lastOpened;
	private boolean open;
	
	public RRCCrateSyncPacket()
	{
		;
	}
	
	public RRCCrateSyncPacket(int x, int y, int z, int ticks, int lastOpened, boolean open)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.ticks = ticks;
		this.lastOpened = lastOpened;
		this.open = open;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		buffer.writeInt(ticks);
		buffer.writeInt(lastOpened);
		buffer.writeBoolean(open);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		ticks = buffer.readInt();
		lastOpened = buffer.readInt();
		open = buffer.readBoolean();
	}
	
	@Override
	public void handleClientSide(EntityPlayer player)
	{
		doSync(player);
	}
	
	@Override
	public void handleServerSide(EntityPlayer player)
	{
		doSync(player);
	}
	
	private void doSync(EntityPlayer player)
	{
		TileEntity tileEntity = player.world.getTileEntity(new BlockPos(x, y, z));
		
		if (tileEntity instanceof TileEntityCrate)
		{
			TileEntityCrate crate = (TileEntityCrate)tileEntity;
			crate.syncValues(ticks, lastOpened, open);
		}
	}
}
