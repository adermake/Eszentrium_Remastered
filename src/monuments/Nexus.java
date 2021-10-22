package monuments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;

public class Nexus extends Monument{
    
	int soulCount = 0;
  

	public Nexus(Player p) {
        super(p,"§eNexus",null);
        range = 6;
      
        platingCount = 0;
        constructMonument();
    }
    
    @Override
    public void onConstruct() {
        //Material TeamMaterial = Material.FIRE_CORAL_BLOCK;
        //Material TeamMaterial2 = Material.CRIMSON_HYPHAE;
        //Material TeamOrbitar = Material.RED_STAINED_GLASS;
        //Material TeamCore = Material.REDSTONE_BLOCK; 
        Material TeamMaterial = Material.TUBE_CORAL_BLOCK;
        Material TeamMaterial2 = Material.LAPIS_BLOCK;
        Material TeamOrbitar = Material.BLUE_STAINED_GLASS;
        Material TeamCore = Material.DIAMOND_BLOCK;
        addBlock(TeamMaterial2, new Vector(0.4,2,0),0.05);
        addBlock(TeamMaterial2, new Vector(-0.4,2,0),0.05);
        addBlock(TeamMaterial, new Vector(0,2,0),0.05);
        addBlock(TeamMaterial2, new Vector(0,2,0.4),0.05);
        addBlock(TeamMaterial2, new Vector(0,2,-0.4),0.05);
        addBlock(TeamMaterial, new Vector(0.6,1.5,0.6),0.05);
        addBlock(TeamMaterial, new Vector(-0.6,1.5,0.6),0.05);
        addBlock(TeamMaterial, new Vector(0.6,1.5,-0.6),0.05);
        addBlock(TeamMaterial, new Vector(-0.6,1.5,-0.6),0.05);
        addBlock(Material.CRACKED_DEEPSLATE_TILES, new Vector(0,2.5,0),0.05);
        

        addBlock(TeamMaterial2, new Vector(0.4,-2,0),-0.05);
        addBlock(TeamMaterial2, new Vector(-0.4,-2,0),-0.05);
        addBlock(TeamMaterial, new Vector(0,-2,0),-0.05);
        addBlock(TeamMaterial2, new Vector(0,-2,0.4),-0.05);
        addBlock(TeamMaterial2, new Vector(0,-2,-0.4),-0.05);
        addBlock(TeamMaterial, new Vector(0.6,-1.5,0.6),-0.05);
        addBlock(TeamMaterial, new Vector(-0.6,-1.5,0.6),-0.05);
        addBlock(TeamMaterial, new Vector(0.6,-1.5,-0.6),-0.05);
        addBlock(TeamMaterial, new Vector(-0.6,-1.5,-0.6),-0.05);
        addBlock(Material.CRACKED_DEEPSLATE_TILES, new Vector(0,-2.5,0),-0.05);
        
        addBlock(TeamOrbitar, new Vector(3,1,0),0.15);
        addBlock(TeamOrbitar, new Vector(-3,1,0),0.15);
        addBlock(TeamOrbitar, new Vector(0,-1,3),0.15);
        addBlock(TeamOrbitar, new Vector(0,-1,-3),0.15);

        addCore(TeamCore, new Vector(0,0,0));

    }
    @Override
    public void onEnter(Player p) {
        playSound(Sound.ENTITY_CHICKEN_DEATH, loc, 100D, 2D);
    }

    @Override
    public void onLeave(Player p) {
        // TODO Auto-generated method stub
        if (p == target) {
            target = null;
        }
    }

    @Override
    public void onEnterEnemy(Player p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onLeaveEnemy(Player p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onActivate(Player p) {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("Activate");
    }

    @Override
    public void coreAnimation(ArmorStand ar,Vector dir) {
        // TODO Auto-generated method stub
        //ParUtils.createFlyingParticle(Particle.END_ROD, ar.getEyeLocation(), 0, 0,0, 1, 5.5F, dir);
        ParUtils.createParticle(Particle.CLOUD, ar.getLocation().add(0,1.5,0), 0, 0, 0, 0, 0);
    }

    @Override
    public void onTick() {
        // TODO Auto-generated method stub
        setMonumentOffset(0,Math.sin(step/15),0);
    }

    public int getSoulCount() {
  		return soulCount;
  	}

  	public void setSoulCount(int soulCount) {
  		this.soulCount = soulCount;
  	}
    

}
