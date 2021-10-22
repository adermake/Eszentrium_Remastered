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
        
        Bukkit.broadcastMessage("bro");
    }
    
    @Override
    public void onConstruct() {
        Material TeamMaterial = Material.CRIMSON_HYPHAE;
        Material TeamCore = Material.RED_STAINED_GLASS;
        addBlock(TeamMaterial, new Vector(0.4,1.5,0),0.05);
        addBlock(TeamMaterial, new Vector(-0.4,1.5,0),0.05);
        addBlock(TeamMaterial, new Vector(0,1.5,0),0.05);
        addBlock(TeamMaterial, new Vector(0,2,0),0.05);
        addBlock(TeamMaterial, new Vector(0,1.5,0.4),0.05);
        addBlock(TeamMaterial, new Vector(0,1.5,-0.4),0.05);

        addBlock(TeamMaterial, new Vector(0.4,-1.5,0),-0.05);
        addBlock(TeamMaterial, new Vector(-0.4,-1.5,0),-0.05);
        addBlock(TeamMaterial, new Vector(0,-1.5,0),-0.05);
        addBlock(TeamMaterial, new Vector(0,-2,0),-0.05);
        addBlock(TeamMaterial, new Vector(0,-1.5,0.4),-0.05);
        addBlock(TeamMaterial, new Vector(0,-1.5,-0.4),-0.05);
        
        addBlock(TeamCore, new Vector(5,1,0),0.2);
        addBlock(TeamCore, new Vector(-5,1,0),0.2);
        addBlock(TeamCore, new Vector(0,-1,5),-0.2);
        addBlock(TeamCore, new Vector(0,-1,-5),-0.2);

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
