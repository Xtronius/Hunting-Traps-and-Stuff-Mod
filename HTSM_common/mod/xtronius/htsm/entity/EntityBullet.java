package mod.xtronius.htsm.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {
  private int bulletdamage;
  private boolean isCrouching;
  private boolean isRunnng;
  public Entity shootingEntity;

  public EntityBullet(World world) {
    super(world);
    this.setSize(0.1F, 0.1F);
  }

  public EntityBullet(World world, EntityLivingBase entityLivingBase, boolean isCrouching, boolean isRunnng, int damage) {
    super(world, entityLivingBase);
    this.bulletdamage = damage;
    this.isCrouching = isCrouching;
    this.isRunnng = isRunnng;
    this.shootingEntity = entityLivingBase;
    this.setSize(0.5F, 0.5F);
    this.setLocationAndAngles(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch);
    this.posX -= MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
    this.posY -= 0.10000000149011612D;
    this.posZ -= MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
    this.setPosition(posX, posY, posZ);
    this.yOffset = 0.0F;
    this.motionX = -MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
    this. motionZ = MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
    this.motionY = (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
    this.setThrowableHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
  }

  public EntityBullet(World world, double xCoord, double yCoord, double zCoord) {
	 super(world, xCoord, yCoord, zCoord);
  }

  @Override
  protected float getGravityVelocity() {
    return 0.001F;
  }

  @Override
  public void setVelocity(double motionX, double motionY, double motionZ) {
	float randX = 0.0F;
	float randY = 0.0F;
	float randZ = 0.0F;
	  
	float rand2X = (float) Math.random();
	float rand2Y = (float) Math.random();
	float rand2Z = (float) Math.random();
	  
	  
	  
	if(!this.isCrouching) {
		randX = (float) Math.random() * 15.0F;
		randX = (float) Math.random() * 15.0F;
		randX = (float) Math.random() * 15.0F;
	}
	
	if(rand2X > 5.0)
		this.motionX = motionX + randX;
	else
		this.motionX = motionX - randX;
	if(rand2X > 5.0)
		this.motionY = motionY + randY;
	else
		this.motionY = motionY - randY;
	if(rand2X > 5.0)
		this.motionZ = motionZ + randZ;
	else
		this.motionZ = motionZ - randZ;
	
	if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
	  float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
	  this.prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
	  this.prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI);
	  this.prevRotationPitch = rotationPitch;
	  this.prevRotationYaw = rotationYaw;
	  
	  setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
	}
  }

  @Override
  protected void onImpact(MovingObjectPosition movingObjectPosition) {
	  
    if (movingObjectPosition.entityHit != null) {
      int var2 = bulletdamage;

      if (movingObjectPosition.entityHit instanceof EntityLivingBase) {
    	  movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), var2);
    	  this.worldObj.playSoundAtEntity(this.shootingEntity, "random.successful_hit", 1.0F, 1.0F);
      }

      if (movingObjectPosition.entityHit instanceof EntityPlayer) {
    	  
    	  this.worldObj.playSoundAtEntity(movingObjectPosition.entityHit, "random.successful_hit", 1.0F, 1.0F);
        if (worldObj.difficultySetting.equals(EnumDifficulty.EASY)) {
          int j = rand.nextInt(10);
          if (j == 0) {
//            ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new EnactEffect(Effect.bleeding.getId(), 20 * 300, 1));
          }
        } else if (worldObj.difficultySetting.equals(EnumDifficulty.NORMAL)) {
          int j = rand.nextInt(5);
          if (j == 0) {
//            ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new EnactEffect(Effect.bleeding.getId(), 20 * 300, 1));
          }
        } else if (worldObj.difficultySetting.equals(EnumDifficulty.HARD)) {
          int j = rand.nextInt(3);
          if (j == 0) {
//            ((EntityLivingBase)movingObjectPosition.entityHit).addPotionEffect(new EnactEffect(Effect.bleeding.getId(), 20 * 300, 1));
          }
        }
      }
    } else if (movingObjectPosition.typeOfHit == MovingObjectType.BLOCK) {
      if (!worldObj.isRemote) {
    	  this.setDead();
      }
      
      Block block = worldObj.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
      
      if (block.getMaterial().equals(Material.glass) || block.getMaterial().equals(Material.plants) || block.getMaterial().equals(Material.vine)) {
    	  this.worldObj.func_147480_a(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ, false);
    	  this.setDead();
      } 
      else if (block != Blocks.air){
    	  int l = this.worldObj.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
          this.worldObj.playAuxSFX(2001, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ, Block.getIdFromBlock(block) + (l << 12));
      } else {
        String stepsound = block.stepSound.getBreakSound();
        this.worldObj.playSoundAtEntity(this, stepsound, 0.25F, 1.0F);
        this.setDead();
      }
    }
  }
}
