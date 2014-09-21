package mod.xtronius.htsm.tileEntity.gui;

import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.containers.ContainerCage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCage extends GuiContainer {
    private static final ResourceLocation cageGuiTextures = new ResourceLocation(Reference.MOD_ASSET, "textures/gui/container/GuiCage.png");

    private static ContainerCage container;

    public GuiCage(IInventory inv, ContainerCage container, World world, int x, int y, int z) {
    	super(container);
        this.ySize = 219;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
//        String s = container.get.isInvNameLocalized() ? invFurnace.getInvName() : I18n.getString(invFurnace.getInvName());
    	String s = "Cage";
        this.fontRendererObj.drawString(s, ((this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2) + 1), 21, 8355711);
//        this.fontRendererObj.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96, 8355711);
        this.fontRendererObj.drawString("inventory", 8, this.ySize - 91, 8355711);
    }
    protected void drawGuiContainerBackgroundLayer(float x, int y, int z) {
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
        this.mc.getTextureManager().bindTexture(cageGuiTextures);
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
