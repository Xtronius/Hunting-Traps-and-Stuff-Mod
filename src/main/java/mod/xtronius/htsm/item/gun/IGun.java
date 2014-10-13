package mod.xtronius.htsm.item.gun;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public interface IGun {
  public int getRounds();

  public int getDamage();

  public String getReloadSound();

  public String getShootSound();

  public Item getAmmo();
  
  public void fireGun(World world, EntityPlayer player, int gunDamage);
}
