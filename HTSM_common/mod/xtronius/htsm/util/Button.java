package mod.xtronius.htsm.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton {
    private final ResourceLocation texture;
    private final int width;
    private final int height;
    private boolean field_146142_r;
    
    private int u;
    private int v;

    protected Button(int id, ResourceLocation texture, int x, int y, int u, int v, int width, int height) {
        super(id, x, y, width, height, "");
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int x, int y) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(this.texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
            int k = 0;

            if (!this.enabled) {
                k += this.width * 2;
            }
            //Is Selected
            else if (this.field_146142_r) {
                k += this.width * 1;
            }
            //Is Highlighted
            else if (this.field_146123_n) {
                k += this.width * 3;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, v, this.width, this.height);

            mc.getTextureManager().bindTexture(this.texture);
            
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.width, this.height, 18, 18);
            
            if(this.field_146123_n)
            	this.func_146111_b(x, y);
        }
    }

    public boolean isSelected() {
        return this.field_146142_r;
    }

    public void setSelected(boolean p_146140_1_) {
        this.field_146142_r = p_146140_1_;
    }
}