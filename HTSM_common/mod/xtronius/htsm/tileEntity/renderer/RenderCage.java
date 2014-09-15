package mod.xtronius.htsm.tileEntity.renderer;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderCage extends TileEntitySpecialRenderer{
	
	
	private ModelCage model;
	private static ResourceLocation texture;
	private Minecraft mc = Minecraft.getMinecraft();
	
	public RenderCage() {
		this.model = new ModelCage();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5f, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		
		texture = new ResourceLocation(Reference.MOD_Gui, "textures/modelTextureMaps/ModelCage_Texture_Map.png");

		this.bindTexture(texture);
		
		this.model.renderModel(0.0625F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
	
		GL11.glPopMatrix();
	}
}

