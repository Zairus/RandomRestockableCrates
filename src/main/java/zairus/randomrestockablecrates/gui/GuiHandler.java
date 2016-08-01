package zairus.randomrestockablecrates.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zairus.randomrestockablecrates.RRCConstants;
import zairus.randomrestockablecrates.client.gui.inventory.GuiCrate;
import zairus.randomrestockablecrates.inventory.ContainerCrate;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case RRCConstants.GUI_CRATE_ID:
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityCrate)
			{
				return new ContainerCrate(player.inventory, ((TileEntityCrate)tileEntity), player);
			}
			break;
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case RRCConstants.GUI_CRATE_ID:
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityCrate)
			{
				return new GuiCrate((TileEntityCrate)tileEntity);
			}
			break;
		}
		
		return null;
	}
}
