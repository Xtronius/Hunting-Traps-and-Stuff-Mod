package mod.xtronius.htsm.util.list;

import java.util.ArrayList;
import java.util.HashMap;

import mod.xtronius.htsm.tileEntity.renderer.model.ModelCage;
import net.minecraft.util.Vec3;

public class ModelCageList {
	
	private HashMap<Vec3, ModelCage> modelCageMap;
	private ArrayList<Vec3> vec3List = new ArrayList<Vec3>();
	
	public ModelCageList() {
		modelCageMap = new HashMap<Vec3, ModelCage>();
	}
	
	public void add(Vec3 vec3, ModelCage model) { modelCageMap.put(vec3, model); vec3List.add(vec3);}	
	public void remove(Vec3 vec3) { modelCageMap.remove(vec3); vec3List.remove(vec3); }
	public ModelCage get(Vec3 vec3) { return modelCageMap.get(vec3); }
	
	public Vec3 getVec3(double x, double y, double z) {
		for(Vec3 vec3 : vec3List) {
			String s = vec3.toString();
				if(s.equals("(" + x + ", " + y + ", " + z + ")")) return vec3;		
		}
		return null;
	}
}