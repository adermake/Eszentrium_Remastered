package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ThermolanzeLaser;

public class Thermolanze extends Spell {

	
	public Thermolanze() {
		cooldown = 20 * 35;
		name = "ßcThermolanze";
		steprange = 30;
		speed =2;
		casttime = 10;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.PROJECTILE);
		addSpellType(SpellType.MULTIHIT);
		setLore("ß7Der Spieler springt in die Luft und#ß7schieﬂt einen Feuerstrahl in Blickrichtung.#ß7Getroffene Gegner werden weggeschleudert.");
		
	}
	Location bLoc;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		SoundUtils.playSound(Sound.ENTITY_IRON_GOLEM_DEATH, caster.getLocation(),1F,50);
		caster.setVelocity(caster.getVelocity().setY(2));
		ParUtils.parKreisDir(Particles.LARGE_SMOKE, loc, 4, 0, 1, new Vector(0,1,0), new Vector(0,1,0));
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		ParUtils.dashParticleTo(Particles.SMOKE, caster, caster.getLocation().add(randVector().multiply(15)));
		caster.setVelocity(caster.getVelocity().add(new Vector(0,0.1F,0)));
	}

	
	@Override
	public void launch() {
		// TODO Auto-generated method stub
	
		
		dirLoc = caster.getLocation();
		dirLoc.setPitch(80);
	}
	Location dirLoc;
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (caster.getGameMode() == GameMode.ADVENTURE)
			dead = true;
		
	
		
		//dirLoc.setDirection(dirLoc.getDirection().add(new Vector(0,0.1F,0)));
		dirLoc.setPitch(caster.getLocation().getPitch()+1);
		dirLoc.setYaw(caster.getLocation().getYaw());
		new ThermolanzeLaser(caster,dirLoc.getDirection(),false);
		playSound(Sound.ENTITY_HUSK_DEATH,loc,10F,1F);
		
		
		
		
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
