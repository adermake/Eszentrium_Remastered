package esze.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class ObjectUtils {

	
	
	public static ArrayList<EszeObject> loadObjects(String name,double delta) {
		
		ArrayList<EszeObject> objs = new ArrayList<EszeObject>();
		 File dir = new File("plugins/Eszentrium/EszeObjects/"+name);
		 File[] directoryListing = dir.listFiles();
		
		 if (directoryListing != null) {
			    for (File child : directoryListing) {
			    	objs.add(loadObject(child,delta));
			    }
			  }
		return objs;
	}
	
	
	public static EszeObject loadObject(String name,double delta) { 
		File f = new File("plugins/Eszentrium/EszeObjects/"+name+".obj");
		
		return loadObject(f,delta);
	}
	
	public static EszeObject loadObject(File f,double delta) { 
	
		ArrayList<Vector> vert = new ArrayList<Vector>();
		ArrayList<Vector> vertNormal = new ArrayList<Vector>();
		ArrayList<Vector> vertNormalAdd = new ArrayList<Vector>();
		ArrayList<EszeFace> faces = new ArrayList<EszeFace>();
		try {
			
			Scanner scanner = new Scanner(f);
			boolean foundVertex = false;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if (line.startsWith("vn ")) {
					line = line.replace("vn ", "");
				
					
					
					String[] values = line.split(" ");
					double x = Double.parseDouble(values[0]);
					double y = Double.parseDouble(values[1]);
					double z = Double.parseDouble(values[2]);
					Vector v = new Vector(x,y,z);
					
					vertNormal.add(v);
				}
				
			
				if (line.startsWith("v ")) {
					line = line.replace("v ", "");
				
					
					
					String[] values = line.split(" ");
					double x = Double.parseDouble(values[0]);
					double y = Double.parseDouble(values[1]);
					double z = Double.parseDouble(values[2]);
					Vector v = new Vector(x,y,z);
					
					vert.add(v);
					vertNormalAdd.add(new Vector(0,0,0));
					
					
				}
				if (line.startsWith("f ")) {
					line = line.replace("f ", "");
				
					
					
					String[] values = line.split(" ");
					EszeFace face = new EszeFace();
					for (String s : values) {
						int vertexID = Integer.parseInt(s.split("/")[0])-1;
						int normalID = Integer.parseInt(s.split("/")[2])-1;
						face.id.add(vertexID);
						vertNormalAdd.get(vertexID).add(vertNormal.get(normalID));
						
						
						
					}
					
					
					faces.add(face);
					
					
				}
			}
			
			int index = 0;
			for (Vector v : vertNormalAdd) {
				
				vert.get(index).add(v.normalize().multiply(-delta));
				
				index++;
			}
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EszeObject(vert, faces);
	}
}
