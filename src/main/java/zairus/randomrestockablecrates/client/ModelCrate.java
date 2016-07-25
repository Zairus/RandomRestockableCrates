package zairus.randomrestockablecrates.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCrate extends ModelBase
{
	public ModelRenderer Box;
	public ModelRenderer lid;
	
	public ModelCrate()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		Box = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		Box.addBox(0F, 0F, 0F, 14, 14, 14);
		Box.rotationPointX = 1.0F;
		Box.rotationPointY = 2.0F;
		Box.rotationPointZ = 1.0F;
		
		lid = (new ModelRenderer(this, 3, 14)).setTextureSize(64, 64);
		lid.addBox(0F, 0F, 0F, 12, 1, 12);
		lid.rotationPointX = 2.0F;
		lid.rotationPointY = 1.0F;
		lid.rotationPointZ = 2.0F;
	}
	
	public void renderAll()
	{
		Box.render(0.0625F);
		lid.render(0.0625F);
	}
}
