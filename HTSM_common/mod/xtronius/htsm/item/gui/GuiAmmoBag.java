package mod.xtronius.htsm.item.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod.xtronius.htsm.item.container.ContainerAmmoBag;
import mod.xtronius.htsm.item.inventory.InventoryAmmoBag;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.tileEntity.gui.GuiCage;
import mod.xtronius.htsm.util.NBTHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAmmoBag extends GuiContainer {
    private final ItemStack parentItemStack;
    private final InventoryAmmoBag inventoryAmmoBag;
    
    public static final ResourceLocation ammoBagGuiTexture = new ResourceLocation(Reference.MOD_ASSET, "textures/gui/container/GuiAmmoBag.png");

    public GuiAmmoBag(EntityPlayer entityPlayer, InventoryAmmoBag inventoryAmmoBag) {
        super(new ContainerAmmoBag(entityPlayer, inventoryAmmoBag));

        this.parentItemStack = inventoryAmmoBag.parentItemStack;
        this.inventoryAmmoBag = inventoryAmmoBag;

        if (this.parentItemStack.getItemDamage() == 0) {
            xSize = 230;
            ySize = 186;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        if (this.parentItemStack.getItemDamage() == 0 || this.parentItemStack.getItemDamage() == 1) {
            fontRendererObj.drawString(StatCollector.translateToLocal(inventoryAmmoBag.getInventoryName()), 8, 6, 4210752);
            fontRendererObj.drawString(StatCollector.translateToLocal("Inventory"), 35, ySize - 95 + 2, 4210752);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.parentItemStack.getItemDamage() == 0) {
            this.mc.getTextureManager().bindTexture(ammoBagGuiTexture);
        }

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        if (mc.thePlayer != null) {
            for (ItemStack itemStack : mc.thePlayer.inventory.mainInventory) {
                if (itemStack != null) {
                    if (NBTHelper.hasTag(itemStack, "AmmoBagOpen")) {
                        NBTHelper.removeTag(itemStack, "AmmoBagOpen");
                    }
                }
            }
        }
    }
}
