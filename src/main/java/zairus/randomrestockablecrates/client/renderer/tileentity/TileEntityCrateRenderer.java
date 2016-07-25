package zairus.randomrestockablecrates.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import zairus.randomrestockablecrates.RRCConstants;
import zairus.randomrestockablecrates.block.BlockCrate;
import zairus.randomrestockablecrates.client.ModelCrate;
import zairus.randomrestockablecrates.tileentity.TileEntityCrate;

public class TileEntityCrateRenderer extends TileEntitySpecialRenderer<TileEntityCrate>
{
	private static final ResourceLocation cratetier1 = new ResourceLocation(RRCConstants.MODID, "textures/entity/crate/crate_tier1.png");
	private static final ResourceLocation cratetier2 = new ResourceLocation(RRCConstants.MODID, "textures/entity/crate/crate_tier2.png");
	private static final ResourceLocation cratetier3 = new ResourceLocation(RRCConstants.MODID, "textures/entity/crate/crate_tier3.png");
	private static final ResourceLocation cratetier4 = new ResourceLocation(RRCConstants.MODID, "textures/entity/crate/crate_tier4.png");
	
	private ModelCrate crate = new ModelCrate();
	
	public TileEntityCrateRenderer()
	{
		;
	}
	
	@Override
	public void renderTileEntityAt(TileEntityCrate te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;
		
		if (!te.hasWorldObj())
		{
			i = 0;
		}
		else
		{
			Block block = te.getBlockType();
			i = te.getBlockMetadata();
			
			if (block instanceof BlockCrate && i == 0)
			{
				((BlockChest)block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
                i = te.getBlockMetadata();
			}
		}
		
		if (destroyStage >= 0)
		{
			this.bindTexture(DESTROY_STAGES[destroyStage]);
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scale(4.0F, 4.0F, 1.0F);
			GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		
		switch(te.getTier())
		{
		case 2:
			this.bindTexture(cratetier2);
			break;
		case 3:
			this.bindTexture(cratetier3);
			break;
		case 4:
			this.bindTexture(cratetier4);
			break;
		default:
			this.bindTexture(cratetier1);
			break;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		
		if (destroyStage < 0)
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		
		int j = 0;
		
		if (i == 2)
		{
			j = 180;
		}
		
		if (i == 3)
		{
			j = 0;
		}
		
		if (i == 4)
		{
			j = 90;
		}
		
		if (i == 5)
		{
			j = -90;
		}
		
		GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		
		if (te.getIsOpen())
			crate.lid.isHidden = true;
		else
			crate.lid.isHidden = false;
		
		crate.renderAll();
		
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		if (destroyStage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
}
