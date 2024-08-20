package monuments;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;

import esze.utils.ParUtils;

public class NexusBeam {

	
	double percentage = 0; // percentage of red beam --- rest is blue
	double redadvantage = 0;
	double blueadvantage = 0;
	double pushBreak = 100;
	Nexus red;
	Nexus blue;
	int step = 0;


	public NexusBeam(Nexus red,Nexus blue) {
		this.red = red;
		this.blue = blue;
		percentage = 0.5D;
	}
	
	
	
	public void calculateBeam() {
		double redSouls = red.getSoulCount();
		double blueSouls = blue.getSoulCount();
		double totalSouls = redSouls+blueSouls;
		redadvantage = 100*redSouls/totalSouls;
		blueadvantage = 100*blueSouls/totalSouls;
	}
	
	
	
	public void displayBeam() {
		percentage += redadvantage/(100 * pushBreak) -blueadvantage/(100 * pushBreak); 
		
		Location redLoc = getCoreLoc(red);
		Location blueLoc = getCoreLoc(blue);
		Location middlePoint = redLoc.clone().add(blueLoc.toVector().subtract(redLoc.toVector()).multiply(percentage));
		ParUtils.parLineRedstone(redLoc, middlePoint, Color.RED, 1.5F, 0.5F);
		ParUtils.parLineRedstone(blueLoc, middlePoint, Color.BLUE, 1.5F, 0.5F);
		ParUtils.createParticle(Particle.EXPLOSION_LARGE, middlePoint, 0.5, 0.5, 0.5, 1, 1);
		step++;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public Location getCoreLoc(Nexus n) {
		for (ArmorStand ar : n.cores.keySet()) {
			return ar.getEyeLocation();
		}
		return null;
	}


}
