package mod.xtronius.htsm.item.gun;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.entity.EntityBullet;
import mod.xtronius.htsm.lib.Reference;
import mod.xtronius.htsm.util.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGun extends Item {
  private IGun gun;

  public ItemGun(IGun iGun) {
    gun = iGun;
    maxStackSize = 1;
    setMaxDamage(gun.getRounds());
  }

  @Override
  public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int i) {
    if (itemstack.getItemDamage() < gun.getRounds()) {
      EntityBullet entitybullet = new EntityBullet(world, player, player.isSneaking(), player.isSprinting(), gun.getDamage());
      world.playSoundAtEntity(player, Reference.MOD_ASSET + ":" + gun.getShootSound(), 1.0F, 1.0F);
      itemstack.damageItem(1, player);

      if (!world.isRemote) {
        world.spawnEntityInWorld(entitybullet);
      }
    } else if (itemstack.getItemDamage() >= gun.getRounds() && player.inventory.hasItem(gun.getAmmo()) && player.isSneaking()) {
      int k = getMaxItemUseDuration(itemstack) - i;
      float f1 = k / 20F;
      f1 = (f1 * f1 + f1 * 2.0F) / 1.5F;
      if (f1 >= 1.0F) {
        itemstack.setItemDamage(0);
        world.playSoundAtEntity(player, Reference.MOD_ASSET + ":" + gun.getReloadSound(), 1.0F, 1.0F);
        player.inventory.consumeInventoryItem(gun.getAmmo());
      }
    } else if(itemstack.getItemDamage() >= gun.getRounds() && player.inventory.hasItem(HTSM.htsmItem.getItemByName("ItemAmmoBag")) && player.isSneaking()) {
    	ItemStack ammoPack = null;
    	for(int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
    		ItemStack stack = player.inventory.getStackInSlot(slot);
    		if(stack != null && stack.getItem().equals(HTSM.htsmItem.getItemByName("ItemAmmoBag"))) {
    			ammoPack = stack;
    			break;
    		}
    	}
    	
    	if(ammoPack != null) {
    		if(ammoPack.stackTagCompound != null) {
    			
    			ItemStack[] array = new ItemStack[48];
    			
    			ItemHelper.loadItemsToArray(ammoPack.stackTagCompound, array);
    			
    			if(array != null) {
    				ItemStack ammo = null;
    				
    				for(int slot = 0; slot < array.length; slot++) {
    					if(array[slot] != null) {
    						System.out.println("stugg" + array[slot]);
    						ammo = array[slot];
    						if(array[slot].stackSize > 1) {
    							array[slot].stackSize--;
    						} else {
    							array[slot] = null;
    						}
    						break;
    					}
    				}
    				
    				if(ammo != null) {
				    	int k = getMaxItemUseDuration(itemstack) - i;
				        float f1 = k / 20F;
				        f1 = (f1 * f1 + f1 * 2.0F) / 1.0F;
				
				        if (f1 >= 1.0F) {
				          itemstack.setItemDamage(0);
				          world.playSoundAtEntity(player, Reference.MOD_ASSET + ":" + gun.getReloadSound(), 1.0F, 1.0F);
				          ItemHelper.saveItemsToNBT(ammoPack.stackTagCompound, array);
				        }
    				}
    			}
    		}
    	}
    } else if(!player.isSneaking()) {
    
      world.playSoundAtEntity(player, "random.click", 1.5F, 1.5F);
    }
  }

  @Override
  public int getMaxItemUseDuration(ItemStack itemstack) {
    return 0x11940;
  }

  @Override
  public EnumAction getItemUseAction(ItemStack itemstack) {
    if (itemstack.getItemDamage() < gun.getRounds()) {
      return null;
    } else {
      return EnumAction.block;
    }
  }

  @Override
  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
    return itemstack;
  }
}