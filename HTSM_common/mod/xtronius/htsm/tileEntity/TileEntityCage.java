package mod.xtronius.htsm.tileEntity;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;
import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.entity.ai.EntityAIGoToCage;
import mod.xtronius.htsm.packet.PacketCageData;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCage extends TileEntity implements IInventory{
	
	private ItemStack[] cageContents = new ItemStack[3];

	private boolean isCageClosed = false;
	
	private NBTTagCompound entityData = null;
	
	private EntityLiving targetEntity;
	
	/**Client-Side Variable*/
	public String targetEntityID;
	
	private ArrayList<Entity> removedEntities = new ArrayList<Entity>();
	
	private int timer = 0;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			float seconds = 1.5f;
			int delay = (int) (seconds * 20);
			
			if(timer <= delay) timer++;
			else timer = 0;
			
//				this.targetEntity = null;
//				this.isCageClosed = false;
//				
//				//TODO Remove test code
//				System.out.println("Target: " + targetEntity);
//				System.out.println("IsClosed: " + isCageClosed);
			
			if(timer == delay) {
				
				//Checks to see if the inv slots are filled
				if(this.getStackInSlot(0) != null || this.getStackInSlot(1) != null || this.getStackInSlot(2) != null) { //TODO Create an item stack array for checking specific items
					if(!this.isCageClosed) {
						//Gets the block at the tile entities' postion
						Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
						
						//Gets the bounding box of the block obtained above, then it offsets it and expands it
						AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
						AxisAlignedBB aabb1 = aabb.getOffsetBoundingBox(this.xCoord, this.yCoord, this.zCoord);
						AxisAlignedBB aabb2 = aabb1.expand(4, 4, 4);
						
						//Gets all entities within this bounding box
						List<EntityPig> list = this.worldObj.getEntitiesWithinAABB(EntityPig.class, aabb2);
						
						for(EntityPig entity : list) {
							
							// Breaks if the cage is closed, if the entity is already in another list or if there is already a targeted entity;
							if(isCageClosed || this.targetEntity != null) break;
		
							if(!this.removedEntities.contains(entity)) {
								//Math for calculating the percentage chance
								double rand = Math.random();
								double randPercent = rand < 0.75 ? rand : 0.75;
								double item = 1.0;
								double subPercent = (item * randPercent) < 1.0 ? (item * randPercent) : 1.0;
								double finalPercent = 1 - subPercent;
								
//									double rand2 = Math.random();
								double rand2 = 1.0;
								
								if(rand2 > finalPercent) {
									//Try to send the entity to the tile entities location, closes the gate and set the entity as the target entity
									entity.getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
									this.isCageClosed = true;
									this.targetEntity = entity;
									break;
								} else {
									this.removedEntities.add(entity);
								}
							}
						}
					} else if(this.isCageClosed) {
						
						int range = 3;
						
						if(this.targetEntity != null) {
							if(this.targetEntity.posX > this.xCoord - range && this.targetEntity.posX < this.xCoord + range) {
								if(this.targetEntity.posY > this.yCoord - range && this.targetEntity.posY < this.yCoord + range) {
									if(this.targetEntity.posZ > this.zCoord - range && this.targetEntity.posZ < this.zCoord + range) {
										if(this.targetEntity != null) {
											NBTTagCompound compound2 = new NBTTagCompound();
									        compound2.setString("id", EntityList.getEntityString(this.targetEntity));
									        this.targetEntity.writeToNBT(compound2);
									        this.entityData = compound2;
											this.targetEntity.setDead();
										}
											this.targetEntity.readFromNBT(this.targetEntity.getEntityData());
									} else
										if(this.targetEntity != null) ((EntityPig) this.targetEntity).getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
								} else
									if(this.targetEntity != null) ((EntityPig) this.targetEntity).getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
							} else
								if(this.targetEntity != null) ((EntityPig) this.targetEntity).getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
						}
					}
				} else this.releaseEntity();
				updateClient();
			}
		}
	}
	
	private void resetVars() { this.isCageClosed = false; this.targetEntity = null; this.entityData = null;}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);   
        
        NBTTagList list = compound.getTagList("Items", 10);
        this.cageContents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound compound1 = list.getCompoundTagAt(i);
            int j = compound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.cageContents.length) {
                this.cageContents[j] = ItemStack.loadItemStackFromNBT(compound1);
            }
        }
        
        NBTTagCompound compound2 = (NBTTagCompound) compound.getTag("EntityData");
        this.entityData = compound2;
        
        if(this.entityData != null)
        	this.isCageClosed = true;
    }

	@Override
    public void writeToNBT(NBTTagCompound compound) {
		
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < this.cageContents.length; ++i) {
            if (this.cageContents[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.cageContents[i].writeToNBT(compound1);
                list.appendTag(compound1);
            }
        }
        
        compound.setTag("Items", list);
        
        if(this.entityData != null) {
	        compound.setTag("EntityData", this.entityData);
        }
    }
	
	public EntityLiving releaseEntity() {
		if(this.entityData != null) {
			EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(this.entityData.getString("id"), this.worldObj);
			entityLiving.readFromNBT(this.entityData);
			this.worldObj.spawnEntityInWorld(entityLiving);
			entityLiving.playLivingSound();
			this.resetVars();
		}
		return this.targetEntity;
	}
	
	private void updateClient() {
		if(this.entityData != null) {
//			HTSM.cageData.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
//			HTSM.cageData.get(Side.SERVER).writeOutbound(new PacketCageData(this.entityData.getString("id"), this.xCoord, this.yCoord, this.zCoord));
			
			HTSM.network.sendToAll(new PacketCageData(this.entityData.getString("id"), this.xCoord, this.yCoord, this.zCoord));
		}
	}

	@Override
	public int getSizeInventory() { return 3; }

	@Override
	public ItemStack getStackInSlot(int slot) { return this.cageContents[slot]; }

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		 if (this.cageContents[p_70298_1_] != null)
	        {
	            ItemStack itemstack;

	            if (this.cageContents[p_70298_1_].stackSize <= p_70298_2_)
	            {
	                itemstack = this.cageContents[p_70298_1_];
	                this.cageContents[p_70298_1_] = null;
	                this.markDirty();
	                return itemstack;
	            }
	            else
	            {
	                itemstack = this.cageContents[p_70298_1_].splitStack(p_70298_2_);

	                if (this.cageContents[p_70298_1_].stackSize == 0)
	                {
	                    this.cageContents[p_70298_1_] = null;
	                }

	                this.markDirty();
	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) { return this.cageContents[slot]; }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) { this.cageContents[slot] = stack; }

	@Override
	public String getInventoryName() { return "Cage"; }

	@Override
	public boolean hasCustomInventoryName() { return false; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }
}
