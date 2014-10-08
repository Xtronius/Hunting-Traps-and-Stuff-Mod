package mod.xtronius.htsm.tileEntity;

import net.minecraftforge.common.util.ForgeDirection;

public interface ITileEntityRotatable {
	
	/** Gets the orientation of the block */
	public ForgeDirection getOrientation();
	
	/** Sets the orientation of the block with the ForgeDirection */
    public void setOrientation(ForgeDirection orientation);
    
    /** Sets the orientation of the block with an int */
    public void setOrientation(int orientation);
}
