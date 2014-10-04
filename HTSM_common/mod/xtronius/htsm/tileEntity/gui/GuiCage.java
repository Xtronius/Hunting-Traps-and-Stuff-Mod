package mod.xtronius.htsm.tileEntity.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.packet.PacketToggleCageGate;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.containers.ContainerCage;
import mod.xtronius.htsm.util.Button;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCage extends GuiContainer {
    public static final ResourceLocation cageGuiTexture = new ResourceLocation(Reference.MOD_ASSET, "textures/gui/container/GuiCage.png");

    private static final Logger logger = LogManager.getLogger();
    
    private static ContainerCage container;
    
    private boolean isStartup = true;
    
    private static boolean isGateClosed;
    
    private int x;
    private int y;
    private int z;
    
    private GuiCage.ReleaseButton releaseButton;
    private boolean buttonsNotDrawn;

    public GuiCage(IInventory inv, ContainerCage container, World world, int x, int y, int z) {
    	super(container);
        this.ySize = 219;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.releaseButton = new GuiCage.ReleaseButton(-1, this.guiLeft + 78, this.guiTop + 93, 1, 222));
        this.buttonsNotDrawn = true;
        this.releaseButton.enabled = true;
        
	      TileEntityCage tileEntity = (TileEntityCage) this.mc.theWorld.getTileEntity(this.x, this.y, this.z);
	        
	      if(tileEntity != null) {
	    	  this.isGateClosed = tileEntity.isCageClosed();
	      }
    }
    
    public void updateScreen() {
        super.updateScreen();
        
        TileEntityCage tileEntity = (TileEntityCage) this.mc.theWorld.getTileEntity(this.x, this.y, this.z);
        
        if(tileEntity != null) {
        	this.isGateClosed = tileEntity.isCageClosed();
        	this.releaseButton.setSelected(this.isGateClosed);
        }
    }
    
    private void updateGate(Boolean toggle) {
    	HTSM.ch.INSTANCE.sendToServer(new PacketToggleCageGate(toggle, this.x, this.y, this.z));
    }
    
    protected void actionPerformed(GuiButton button) {
        if (button.id == -1) {
        	TileEntityCage tileEntity = (TileEntityCage) this.mc.theWorld.getTileEntity(this.x, this.y, this.z);
            
            if(tileEntity != null) {
	    		boolean flag = !tileEntity.isCageClosed();
	    		
	    		updateGate(flag);
	    		this.isGateClosed = flag;
            }
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	String s = "Cage";
        this.fontRendererObj.drawString(s, ((this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2) + 1), 21, 8355711);
        this.fontRendererObj.drawString("inventory", 8, this.ySize - 91, 8355711);
    }
    
    protected void drawGuiContainerBackgroundLayer(float x, int y, int z) {
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.625F);
        this.mc.getTextureManager().bindTexture(cageGuiTexture);
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
    
    @SideOnly(Side.CLIENT)
    class ReleaseButton extends Button {

        public ReleaseButton(int id, int x, int y, int u, int v) {
            super(id, GuiCage.cageGuiTexture, x, y, u, v, 22, 22);
        }

        public void func_146111_b(int x, int y) {
        		String s = GuiCage.isGateClosed ? "Open Cage" : "Close Cage";
        		GuiCage.this.drawCreativeTabHoveringText(s, x, y);
        }
    }
}
