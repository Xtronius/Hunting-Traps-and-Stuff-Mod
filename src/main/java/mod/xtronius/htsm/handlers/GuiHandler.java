package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.item.container.ContainerAmmoBag;
import mod.xtronius.htsm.item.gui.GuiAmmoBag;
import mod.xtronius.htsm.item.inventory.InventoryAmmoBag;
import mod.xtronius.htsm.tileEntity.TileEntityCage;
import mod.xtronius.htsm.tileEntity.containers.ContainerCage;
import mod.xtronius.htsm.tileEntity.gui.GuiCage;
import mod.xtronius.htsm.tileEntity.gui.GuiIDs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	
		switch(id) {
			case GuiIDs.guiCageID: {		
				return new ContainerCage((TileEntityCage) world.getTileEntity(x, y, z), player.inventory, world, x, y, z);
			}
			case GuiIDs.guiAmmoBag: {		
				return new ContainerAmmoBag(player, new InventoryAmmoBag(player.getHeldItem()));
			}
		}
		return null;
	}
	
	
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
	
		switch(id) {
			case GuiIDs.guiCageID: {
				return new GuiCage(player.inventory, new ContainerCage((TileEntityCage) world.getTileEntity(x, y, z), player.inventory, world, x, y, z), world, x, y, z);
			}
			case GuiIDs.guiAmmoBag: {
				return new GuiAmmoBag(player, new InventoryAmmoBag(player.getHeldItem()));
			}
		}
		return null;
	}
}