package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.Matrix;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class RuneDraw extends Spell {

	ArrayList<Vector> offsets = new ArrayList<Vector>();
	
	public RuneDraw() {
		steprange = 20 * 2;
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	Vector origin;
	Vector normalVec;
	Vector vec;
	double neg = 1;
	double vlen = 5;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		vec = caster.getLocation().getDirection().multiply(vlen);
		normalVec = caster.getLocation().getDirection();
		//neg = Math.signum(normalVec.getX()*normalVec.getY()*normalVec.getZ());
		yang =  singedangle(caster.getLocation().getDirection(),new Vector(0,1,0));
		//Bukkit.broadcastMessage(""+yang);
	}
	
	double yang;
	@Override
	public void move() {
		
		//x = 1
		
		
		
		//ParUtils.debugRay(locC);
		
		// TODO Auto-generated method stub
		Vector rotVec = getRotVec();
		//Matrix.alignLocRotation(l, relative)
		Vector v = caster.getEyeLocation().getDirection().multiply(vlen);
		v = v.rotateAroundAxis(rotVec, yang);
		v = v.multiply(vlen/v.getY());
		v = v.rotateAroundAxis(rotVec, -yang);
		//double yang = v.angle(new Vector(0,1,0));
		
		//Bukkit.broadcastMessage(""+v.clone().subtract(vec));
		//double xang = v.angle(new Vector(1,0,0));
		//double zang = v.angle(new Vector(0,0,1));
		
		
		offsets.add(v);
		//ArrayList<Vector> removeLater = new ArrayList<Vector>();
		if (offsets.size() > 50) {
			offsets.remove(0);
		}
		
		shapedetector();
		for (Vector vec : offsets) {
			
			ParUtils.createParticle(Particle.WATER_BUBBLE, caster.getEyeLocation().add(vec), 0, 0, 0, 5, 0);
			
			
			
		}
		double distance = getAvgDistanceToCenter();
		//Bukkit.broadcastMessage(""+distance);
	}
	
	
	public Vector getRotVec() {
		double x1 = - normalVec.getZ()*1/normalVec.getX();
		Vector rotVec = new Vector(x1,0,1);
		return rotVec;
	}
	public double singedangle(Vector v,Vector v2) {
		return Math.atan2(v2.getY(),v2.getX()) - Math.atan2(v.getY(),v.getX());
	}
	public Vector getCenter() {
		Vector v = new Vector(0,0,0);
		for (Vector vec : offsets) {
			v.add(vec);
		}
		v.multiply(1D/(double)offsets.size());
		
		return v;
	}
	
	public double getAvgDistanceToCenter() {
		Vector v = getCenter();
		double distance = 0;
		for (Vector vec : offsets) {
			distance += v.distance(vec);
		}
		return distance/(double)offsets.size();
	}
	public int[][] getGridValues(int acc) {
		int[][] ar = new int[20*acc][20*acc];
		
		for (Vector v : offsets) {
			Vector p = v.clone().rotateAroundAxis(v, yang);
			
			int x = (int)(p.getX()*(double)acc);
			//int y = (int)(p.getX()*(double)acc);
			int z = (int)(p.getZ()*(double)acc);
			if (ar.length > x && ar[x].length > z) {
				ar[x][z] = 1;
			}
		}
		return ar;
	}
	public ArrayList<Vector> getGridValuesVec(int acc) {
		ArrayList<Vector> vecs = new ArrayList<Vector>();
		Vector subber = vec.clone().rotateAroundAxis(getRotVec(), yang);
		for (Vector v : offsets) {
			Vector p = v.clone().rotateAroundAxis(getRotVec(), yang).subtract(subber);
			p.setY(0);
			p.setX(10*acc+p.getX()*acc);
			p.setZ(10*acc+p.getZ()*acc);
			Bukkit.broadcastMessage(""+p);
			if (p.getX()>0 && p.getX() < 20*acc && p.getZ()>0 && p.getZ() < 20*acc ) {
				//Bukkit.broadcastMessage("ADDED ");
				vecs.add(p);
			}
			
		}
		
		return vecs;
	}
	
	public void shapedetector() {
		int acc = 1;
		
		int[][] ar = new int[20*acc][20*acc];
		ArrayList<Vector> dots = getGridValuesVec(acc);
		for (int x = 0;x<ar.length;x++) {
			for (int y = 0;y<ar[x].length;y++) {
				ar[x][y] = 0;
			}
		}
		//INC
		double tacc = 64;
		for (Vector v : dots) {
			int x = (int) v.getX();
			int y = (int) v.getZ();
			//Bukkit.broadcastMessage("xv");
			for (double theta = -0.5*Math.PI;theta < 0.5*Math.PI;theta+= Math.PI/tacc) {
				Bukkit.broadcastMessage("x");
				double r = x*Math.cos(theta) + y*Math.sin(theta);
				int ir = (int) (r*acc);
				int it = (int) (theta*acc);
				ar[ir][it]+=1;
			}
		}
		//MAX
		int rmax = 0;
		int tmax = 0;
		int max = 0;
		for (int x = 0;x<ar.length;x++) {
			for (int y = 0;y<ar[x].length;y++) {
				if (ar[x][y] > max) {
					rmax = x;
					tmax = y;
					max = ar[x][y];
				}
			}
		}
		
		//Bukkit.broadcastMessage("found "+ max +" dots in line");
		
	}
	
	

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
