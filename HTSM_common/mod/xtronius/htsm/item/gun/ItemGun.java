package mod.xtronius.htsm.item.gun;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.entity.EntityBullet;
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
  public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
    if (itemstack.getItemDamage() < gun.getRounds()) {
      EntityBullet entitybullet = new EntityBullet(world, entityplayer, gun.getDamage());
//      TODO Play Gun Sound
      itemstack.damageItem(1, entityplayer);

      if (!world.isRemote) {
        world.spawnEntityInWorld(entitybullet);
      }
    } else if (itemstack.getItemDamage() >= gun.getRounds() && entityplayer.inventory.hasItem(gun.getAmmo())) {
      int k = getMaxItemUseDuration(itemstack) - i;
      float f1 = k / 20F;
      f1 = (f1 * f1 + f1 * 2.0F) / 3F;

      if (f1 >= 1.0F) {
        itemstack.setItemDamage(0);
//       TODO Play Gun Sound
        entityplayer.inventory.consumeInventoryItem(gun.getAmmo());
      }
    } else {
      world.playSoundAtEntity(entityplayer, "random.click", 1.0F, 1.0F);
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