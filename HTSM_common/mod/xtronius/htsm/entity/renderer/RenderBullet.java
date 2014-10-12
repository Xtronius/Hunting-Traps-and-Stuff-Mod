package mod.xtronius.htsm.entity.renderer;

import mod.xtronius.htsm.entity.renderer.model.ModelBullet;
import mod.xtronius.htsm.lib.ConfigValues;
import mod.xtronius.htsm.lib.Reference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBullet extends Render {
  private ModelBullet modelBullet;

  public RenderBullet(ModelBullet modelBase) {
    modelBullet = modelBase;
  }

  public void renderBullet(Entity entity, double x, double y, double z) {
    GL11.glPushMatrix();
    GL11.glDisable(GL11.GL_CULL_FACE);
    GL11.glTranslatef((float)x-1, (float)y, (float)z);
    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    GL11.glScalef(0.03125F, 0.03125F, 0.03125F);
    GL11.glEnable(GL11.GL_ALPHA_TEST);
    bindEntityTexture(entity);
    modelBullet.render(0.0625F);
    GL11.glPopMatrix();
  }

  @Override
  protected ResourceLocation getEntityTexture(Entity par1Entity) {
    return new ResourceLocation(Reference.MOD_ASSET + ":textures/entities/bullet.png");
  }

  @Override
  public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
	  if(ConfigValues.RenderBullet)
		  renderBullet(entity, x, y, z);
  }


}
