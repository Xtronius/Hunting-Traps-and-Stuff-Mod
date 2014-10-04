package mod.xtronius.htsm.tileEntity.renderer;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.TileEntitySpike;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelSpike;
import mod.xtronius.htsm.util.ResourceLocationHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpike extends TileEntitySpecialRenderer {
    private final ModelSpike modelSpike;
    private ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelBlockSpike_Texture_Map_2.png");

    public RenderSpike() {
    	 modelSpike = new ModelSpike();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        if (tileEntity instanceof TileEntitySpike) {
        	TileEntitySpike tileEntitySpike = (TileEntitySpike) tileEntity;

            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glPushMatrix();
            renderSpikeByOrientation(x, y, z, tileEntitySpike.getOrientation());
            this.bindTexture(texture);
            modelSpike.render();
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }

    private void renderSpikeByOrientation(double x, double y, double z, ForgeDirection forgeDirection)
    {
        switch (forgeDirection)
        {
            case DOWN:
            {
            	GL11.glTranslatef((float) x + 0.0625F, (float) y + 1.0F, (float) z + 0.9375F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(-180F, 1F, 0F, 0F);
                return;
            }
            case UP:
            {
            	GL11.glTranslatef((float) x + 0.9375F, (float) y + 0.0F, (float) z + 0.9375F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                return;
            }
            case NORTH:
            {
            	GL11.glTranslatef((float) x + 0.0625F, (float) y + 0.0625F, (float) z + 1.0F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(-90F, 1F, 0F, 0F);
                return;
            }
            case SOUTH:
            {
            	GL11.glTranslatef((float) x + 0.0625F, (float) y + 0.9375F, (float) z + 0.0F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
                return;
            }
            case EAST:
            {
            	GL11.glTranslatef((float) x + 0.0F, (float) y + 0.9375F, (float) z + 0.0625F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(-90F, 0F, 0F, 1F);
                return;
            }
            case WEST:
            {
            	GL11.glTranslatef((float) x + 1.0F, (float) y + 0.0625F, (float) z + 0.0625F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(90F, 0F, 0F, 1F);
                return;
            }
            case UNKNOWN:
            {
                return;
            }
            default:
            {
            }
        }
    }
}
