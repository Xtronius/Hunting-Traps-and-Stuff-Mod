package mod.xtronius.htsm.item.gun;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.entity.EntityBullet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemShotgun implements IGun {
  @Override
  public int getRounds() {
    return 3;
  }

  @Override
  public int getDamage() {
    return 4;
  }

  @Override
  public String getReloadSound() {
    return "Shotgun_Reload";
  }

  @Override
  public String getShootSound() {
    return "Shotgun_Fire";
  }

  @Override
  public Item getAmmo() {
    return HTSM.htsmItem.getItemByName("ItemShotGunAmmo");
  }

	@Override
	public void fireGun(World world, EntityPlayer player, int gunDamage) {
		
		  EntityBullet entitybullet = new EntityBullet(world, player, gunDamage);
	      EntityBullet entitybullet2 = new EntityBullet(world, player, gunDamage);
	      EntityBullet entitybullet3 = new EntityBullet(world, player, gunDamage);
	      EntityBullet entitybullet4 = new EntityBullet(world, player, gunDamage);
	      EntityBullet entitybullet5 = new EntityBullet(world, player, gunDamage);
	      
		if(!world.isRemote) {
			world.spawnEntityInWorld(entitybullet);
	        world.spawnEntityInWorld(entitybullet2);
	        world.spawnEntityInWorld(entitybullet3);
	        world.spawnEntityInWorld(entitybullet4);
	        world.spawnEntityInWorld(entitybullet5);
		}
	}
}