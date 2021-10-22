package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.Matrix;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Heilen extends Spell{
	int s = 9;
	
	public Heilen() {
		steprange = 70;
		cooldown = 20*24;
		name = "§aHeilen";
		speed = 30;
		
		hitboxSize = 1;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		hitBlock = true;
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.PROJECTILE);
	    setLore("§7Schießt einen Strahl, der einen getroffenen Verbündeten heilt.");
	    setBetterLore("§7Schießt einen Strahl, der einen getroffenen Verbündeten heilt. Wird ein Spieler getroffen, heilt sich der Anwender ebenfalls.");
	}
	public Heilen(int s,boolean ref) {
		super();
		refined = ref;
		this.s = s;
		steprange = 70;
		cooldown = 2;
		name = "§eHeilen";
		speed = 30;
		
		hitboxSize = 1;
		hitSpell = true;
		hitEntity = true;
		hitPlayer = true;
		hitBlock = true;
	}
	
	@Override
	public void setUp() {
		if (refined)
			steprange *= 4;
		// TODO Auto-generated method stub
		loc.setDirection(caster.getLocation().getDirection());
		loc = Matrix.alignLocRotation(loc, new Vector(-0.3,0,0.4));
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		new BukkitRunnable() {
			public void run() {
				if (s>0) {
					Spell h = (Spell) new Heilen(s-1,refined);
					h.castSpell(caster,name);
				}
				
			}
		}.runTaskLater(main.plugin, 3);
		playSound( Sound.ENTITY_ZOMBIE_VILLAGER_CURE,loc, (float) 0.1, 1);
		loc.setDirection(loc.getDirection().multiply(10).add(randVector()).normalize());
	}

	@Override
	public void move() {
		ParUtils.createParticle(Particle.VILLAGER_HAPPY, loc, 0, 0, 0, 1,0);
		loc.add(loc.getDirection().multiply(0.4));
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (refined) {
			heal(p, 5, caster);
		}
		else {
			heal(p, 3, caster);
		}
		
		playSound(Sound.ENTITY_ENDERMITE_DEATH, loc, 2, 1);
		
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_ENDERMITE_DEATH, loc, 2, 1);
		if (refined) {
			heal(ent, 5, caster);
		}
		else {
			heal(ent, 3, caster);
		}
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
		if (refined) {
			heal(caster,1,caster);
			ParUtils.parKreisDirSolid(Particle.TOTEM, loc, 1, 0, 3, loc.getDirection(), loc.getDirection().multiply(-2));
		}
	}
	

}
