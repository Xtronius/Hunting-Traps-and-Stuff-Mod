package mod.xtronius.htsm.handlers;

import mod.xtronius.htsm.entity.ai.EntityAIGoToCage;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HTSMEntityHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) { 
		if(event.entity != null && event.entity instanceof EntityPig) {
			EntityPig entity = (EntityPig) event.entity;
			
//			entity.tasks.addTask(10, new EntityAIGoToCage(entity, 0, Items.gunpowder));
		}
	}
}
