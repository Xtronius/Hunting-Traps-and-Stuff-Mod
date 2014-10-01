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
    private final ModelSpike modelSpike = new ModelSpike();
    private final RenderItem customRenderItem;
    
    public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ASSET, "textures/modelTextureMaps/ModelBlockSpike_Texture_Map_2.png");

    public RenderSpike() {
        customRenderItem = new RenderItem() {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        if (tileEntity instanceof TileEntitySpike) {
        	TileEntitySpike tileEntityGlassBell = (TileEntitySpike) tileEntity;

            GL11.glDisable(GL11.GL_CULL_FACE);

            /**
             * Render the Glass Bell
             */
            GL11.glPushMatrix();

         // Scale, Translate, Rotate
            renderSpikeByOrientation(x, y, z, ForgeDirection.NORTH);
            
            // Bind texture
            this.bindTexture(texture);

            modelSpike.render();

            GL11.glPopMatrix();

            /**
             * Render the ghost item inside of the Glass Bell, slowly spinning
             */
            GL11.glPushMatrix();

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
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslatef((float) x + 0.0F, (float) y + 2.0F, (float) z + 0.0F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
                return;
            }
            case UP:
            {
            	GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
                GL11.glTranslatef((float) x + 0.0F, (float) y + -1.0F, (float) z + 1.0F);
                GL11.glRotatef(-90F, 1F, 0F, 0F);
                return;
            }
            case NORTH:
            {
                GL11.glTranslatef((float) x + 0.9375F, (float) y + 0.0F, (float) z + 0.9375F);
                GL11.glScalef(0.4375F, 0.4375F, 0.4375F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
                return;
            }
            case SOUTH:
            {
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslatef((float) x + 0.0F, (float) y + 0.0F, (float) z + -1.0F);
                return;
            }
            case EAST:
            {
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef((float) x + -1.0F, (float) y + 1.0F, (float) z + 1.0F);
                GL11.glRotatef(-90F, 0F, 0F, 1F);
                GL11.glRotatef(-90F, 1F, 0F, 0F);
                return;
            }
            case WEST:
            {
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef((float) x + 2.0F, (float) y + 0.0F, (float) z + 1.0F);
                GL11.glRotatef(90F, 0F, 0F, 1F);
                GL11.glRotatef(-90F, 1F, 0F, 0F);
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
