package mod.xtronius.htsm.item.gun;

import mod.xtronius.htsm.core.HTSM;
import net.minecraft.item.Item;

public class ItemShotgun implements IGun {
  @Override
  public int getRounds() {
    return 3;
  }

  @Override
  public int getDamage() {
    return 20;
  }

  @Override
  public String getReloadSound() {
    return "reload";
  }

  @Override
  public String getShootSound() {
    return "dbshotgun";
  }

  @Override
  public Item getAmmo() {
    return HTSM.htsmItem.getItemByName("ItemShotGunAmmo");
  }
}