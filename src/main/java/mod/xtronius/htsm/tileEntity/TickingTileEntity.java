package mod.xtronius.htsm.tileEntity;

import net.minecraft.tileentity.TileEntity;

public class TickingTileEntity extends TileEntity {

	protected int timer = 0;
	protected float seconds = 5.0f;
	
	public void updateEntity() {
		if(this.canUpdate()) {
			this.incrementTimer();
			if(timer == getUpdateInterval()) 
				intervalUpdate();
		}
	}
	
	/**Method for updating when the timer ticks hit the timer interval*/
	protected void intervalUpdate() {}
	
	public boolean canUpdate() {
        return false;
    }
	
	/**The interval between each call of the intervalUpdate method*/
	protected int getUpdateInterval() { 
		return (int) (this.seconds * 20);
	}
	
	/**Ticks the timer*/
	protected void incrementTimer() { 
		if(timer <= getUpdateInterval()) timer++;
		else timer = 0; 
	}
}
