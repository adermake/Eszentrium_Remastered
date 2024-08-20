package esze.objects;

import java.util.ArrayList;

import org.bukkit.util.Vector;

public class EszeObject {

	
	public ArrayList<Vector> vert = new ArrayList<Vector>();
	public ArrayList<EszeFace> faces = new ArrayList<EszeFace>();
	
	
	public EszeObject(ArrayList<Vector> v,ArrayList<EszeFace> f) {
		vert = v;
		faces = f;
	}
	
	
	
	public void scale(double d) {
		for (Vector v : vert) {
			v.multiply(d);
		}
	
	}
	
	
	
	
}
