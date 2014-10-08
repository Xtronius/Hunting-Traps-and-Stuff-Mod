package mod.xtronius.htsm.tileEntity;

import java.util.ArrayList;

import mod.xtronius.htsm.core.HTSM;
import mod.xtronius.htsm.handlers.PacketHandler;
import mod.xtronius.htsm.packet.PacketCageData;
import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCage extends TickingTileEntity implements IInventory, ITileEntityRemovable{
	
	private ItemStack[] invContents = new ItemStack[3];

	private boolean isCageClosed = true;
	private boolean isProcessing = false;
	private EntityLiving targetEntity;
	private NBTTagCompound entityData = null;
	private ArrayList<Entity> removedEntities = new ArrayList<Entity>();
	
	/**Client-Side Variable*/
	public String targetEntityID;
	/**Client-Side Variable*/
	public ItemStack displayStack;
	
	private int detectionRange = 10;
	private int captureRange = 3;
	
	public ModelCage model = new ModelCage();
	
	protected void intervalUpdate() {
		if(!this.worldObj.isRemote) {
			if(!this.isCageClosed) {
				//Checks to see if the inv slots are filled
				if(this.doesHaveItem() || this.isProcessing) {
					if(this.targetEntity == null) {
						//Gets the block at the tile entities' postion
						Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
						//Gets the bounding box of the block obtained above, then it offsets it and expands it
						AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
						AxisAlignedBB aabb1 = aabb.getOffsetBoundingBox(this.xCoord, this.yCoord, this.zCoord);
						AxisAlignedBB aabb2 = aabb1.expand(detectionRange, detectionRange, detectionRange);
						//The master list in-which all of the entities within range are put in a nested Array-List
						ArrayList<ArrayList<EntityLiving>> list = new ArrayList<ArrayList<EntityLiving>>();
						
						//Gets all entities within this bounding box and adds them to the master list					
						for(int i = 0; i < this.getSizeInventory(); i++) 
							if(this.getStackInSlot(i) != null && HTSM.cageList.isValidItemStack(this.getStackInSlot(i))) {
								ArrayList<Class> aquEntList = HTSM.cageList.getEntityFromItemStack(this.getStackInSlot(i));
								for(Class entity : aquEntList) list.add((ArrayList<EntityLiving>) this.worldObj.getEntitiesWithinAABB(entity, aabb2));
								break;
							}
						
						for(ArrayList<EntityLiving> array : list) 
							for(EntityLiving entity : array) {
								if(!this.removedEntities.contains(entity)) {
									//Math for calculating the percentage chance
									double basePercent = 0.75;
									double item = 1.0;
									double itemPercent = (item * basePercent) < 1.0 ? (item * basePercent) : 1.0;
									double finalPercent = 1.0 - itemPercent;
									
									double rand = Math.random();
	//								double rand = 1.0d;
									
									//If the randomly calculated value is greater than the 
									if(rand >= finalPercent) {
										System.out.println(rand + " Out of: " + finalPercent);
										//Try to send the entity to the tile entities location, closes the gate and set the entity as the target entity
										entity.getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
										this.targetEntity = entity;
										
										for(int i = 0; i < this.getSizeInventory(); i++) 
											if(this.getStackInSlot(i) != null && this.getStackInSlot(i).stackSize > 0) {
												this.getStackInSlot(i).stackSize--;
												if(this.getStackInSlot(i).stackSize <= 0)
													this.setInventorySlotContents(i, null);
												this.isProcessing = true;
												break;
											} else this.setInventorySlotContents(i, null);
										break;
									} else this.removedEntities.add(entity);
								}
							}
					} else if(this.targetEntity != null) {
						if(this.targetEntity != null) 
							if(this.targetEntity.posX > this.xCoord - captureRange && this.targetEntity.posX < this.xCoord + captureRange) 
								if(this.targetEntity.posY > this.yCoord - captureRange && this.targetEntity.posY < this.yCoord + captureRange) 
									if(this.targetEntity.posZ > this.zCoord - captureRange && this.targetEntity.posZ < this.zCoord + captureRange) 
										if(this.targetEntity != null) {
											NBTTagCompound compound2 = new NBTTagCompound();
									        compound2.setString("id", EntityList.getEntityString(this.targetEntity));
									        this.targetEntity.writeToNBT(compound2);
									        this.setEntityData(compound2);
											this.targetEntity.setDead();
											this.isProcessing = false;
											this.targetEntity.readFromNBT(this.targetEntity.getEntityData());
											this.setCageClosed(true);
											
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord+1, this.yCoord, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord+1, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord+1, HTSM.blockInit.getBlockByName("BlockCage"));
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord-1, this.yCoord, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord-1, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
											this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord-1, HTSM.blockInit.getBlockByName("BlockCage"));
										}
									 else if(this.targetEntity != null) this.targetEntity.getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
								 else if(this.targetEntity != null) this.targetEntity.getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
							 else if(this.targetEntity != null) this.targetEntity.getNavigator().tryMoveToXYZ(this.xCoord, yCoord, zCoord, 1.0f);
					}
				}
				//Updates neighboring blocks for redstone update
				this.updateNeighbors();
				//Updates the Client
				if(this.getEntityData() != null && this.getEntityData().hasKey("id"))
					updateClient(this.getEntityData().getString("id"));
				else
					updateClient("No-Entity");
			}
			
			//Updates the Client
			if(this.getEntityData() != null && this.getEntityData().hasKey("id"))
				updateClient(this.getEntityData().getString("id"));
			else
				updateClient("No-Entity");
		}
	}
	
	public boolean canUpdate() {
        return true;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);   
        
        NBTTagList list = compound.getTagList("Items", 10);
        this.invContents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound compound1 = list.getCompoundTagAt(i);
            int j = compound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.invContents.length)  this.invContents[j] = ItemStack.loadItemStackFromNBT(compound1);
        }
        
        NBTTagCompound compound2 = (NBTTagCompound) compound.getTag("EntityData");
        this.setEntityData(compound2);
        
        NBTTagCompound compound3 = (NBTTagCompound) compound.getTag("Misc");
        this.setCageClosed(compound3.getBoolean("isCageClosed"));
    }

	@Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < this.invContents.length; ++i) {
            if (this.invContents[i] != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.invContents[i].writeToNBT(compound1);
                list.appendTag(compound1);
            }
        }
        
        compound.setTag("Items", list);
        
        NBTTagCompound nbt = new NBTTagCompound();
        
        nbt.setBoolean("isCageClosed", this.isCageClosed());
        
        compound.setTag("Misc", nbt);
        
        if(this.getEntityData() != null) {
	        compound.setTag("EntityData", this.getEntityData());
        }
    }
	
	public void updateNeighbors() {
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord+1, this.yCoord, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord+1, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord+1, HTSM.blockInit.getBlockByName("BlockCage"));
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord-1, this.yCoord, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord-1, this.zCoord, HTSM.blockInit.getBlockByName("BlockCage"));
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord-1, HTSM.blockInit.getBlockByName("BlockCage"));
	}
	
	public boolean releaseEntity() {
		if(this.getEntityData() != null) {
			EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(this.getEntityData().getString("id"), this.worldObj);
			entityLiving.readFromNBT(this.getEntityData());
			entityLiving.setPosition(this.xCoord+1, this.yCoord+1, this.zCoord+1);
			this.worldObj.spawnEntityInWorld(entityLiving);
			entityLiving.playLivingSound();
			this.updateClient("No-Entity");
			this.removedEntities.add(entityLiving);
			this.resetVars();
			return true;
		}
//		this.resetVars();
		return false;
	}
	
	private boolean doesHaveItem() { for(int i = 0; i < 3; i++) if(this.getStackInSlot(i) != null) return true; return false; }
	private void resetVars() { this.targetEntity = null; this.setEntityData(null); this.isProcessing = false;}
	
	private void updateClient(String id) { 
		
		ItemStack stack = null;
		
		for(int i = 0; i < this.getSizeInventory(); i++) if(this.getStackInSlot(i) != null) { stack = this.getStackInSlot(i); break;}
		HTSM.ch.INSTANCE.sendToAll(new PacketCageData(id, this.entityData, stack, this.isCageClosed(), this.xCoord, this.yCoord, this.zCoord)); 
	}
	
	@Override
	public int getSizeInventory() { return 3; }

	@Override
	public ItemStack getStackInSlot(int slot) { return this.invContents[slot]; }

	@Override
	public ItemStack decrStackSize(int slotIndex, int i) {
		 if (this.invContents[slotIndex] != null) {
	            ItemStack itemstack;

	            if (this.invContents[slotIndex].stackSize <= i) {
	                itemstack = this.invContents[slotIndex];
	                this.invContents[slotIndex] = null;
	                this.markDirty();
	                return itemstack;
	            }
	            else {
	                itemstack = this.invContents[slotIndex].splitStack(i);
	                
	                if (this.invContents[slotIndex].stackSize == 0) 
	                    this.invContents[slotIndex] = null;
	                
	                this.markDirty();
	                return itemstack;
	            }
	        }
	        else return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) { return this.invContents[slot]; }

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) { this.invContents[slot] = stack; }

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
	public boolean isItemValidForSlot(int slot, ItemStack stack) { return HTSM.cageList.isValidItemStack(stack); }

	public boolean isCageClosed() { return isCageClosed; }
	public void setCageClosed(boolean isCageClosed) { this.isCageClosed = isCageClosed; }

	public NBTTagCompound getEntityData() { return entityData; }
	public void setEntityData(NBTTagCompound entityData) { this.entityData = entityData; }

	@Override
	public ItemStack removeTileEntity() {
		ItemStack stack = new ItemStack(HTSM.itemInit.getItemByName("ItemCage"));
		stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.stackTagCompound;
		NBTTagList list = new NBTTagList();
        
        for (int i = 0; i < 3; ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound compound1 = new NBTTagCompound();
                compound1.setByte("Slot", (byte)i);
                this.getStackInSlot(i).writeToNBT(compound1);
                list.appendTag(compound1);
            }
        }
        nbt.setTag("CageItems", list);
        
        NBTTagCompound miscTag = new NBTTagCompound();
        
        miscTag.setBoolean("isCageClosed", this.isCageClosed());
        
        nbt.setTag("Misc", miscTag);
        
        if(this.getEntityData() != null) 
	        nbt.setTag("EntityData", this.getEntityData());
        
        for(int i = 0; i < this.getSizeInventory(); i++)
        	this.setInventorySlotContents(i, null);
		
        this.setEntityData(null);
        this.releaseEntity();
        this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		return stack;
	}
}
