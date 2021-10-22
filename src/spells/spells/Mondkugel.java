package spells.spells;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Mondkugel extends Spell {
	
	ArrayList<Entity> hit = new ArrayList<Entity>();
	public Mondkugel() {
		cooldown = 28 * 20;
		name = "§eMondkugel";
		steprange = 50;
		
		hitboxSize = 2;
		speed = 1;
		multihit = true;
		hitSpell = true;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Wirft ein Projektil in Blickrichtung,#§7das nach kurzer Distanz eine 180°-Wende#§7macht. Getroffene Spieler werden mit der#§7Mondkugel mitgezogen.Zerbricht bei Kontakt mit#§7Blöcken.");
		setBetterLore("§7Wirft ein Projektil in Blickrichtung,#§7das nach kurzer Distanz eine 180°-Wende#§7macht. Getroffene Spieler werden mit der#§7Mondkugel mitgezogen.");
	}
	@Override
	public void setUp() {
	
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 1, 2);
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}
	Entity s;
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
		
		
		if (refined) {
			
			ItemStack is = new ItemStack(Material.SNOW_BLOCK);
			Item i = caster.getWorld().dropItem(caster.getLocation(), is);
			i.setPickupDelay(1000000);
			s = i;
		}
		else {
			s = (Snowball) spawnEntity(EntityType.SNOWBALL);
			s.setGravity(false);
			s.setGlowing(true);
			s.setInvulnerable(true);
		}
		
	}
	
	float div = 2;
	@Override
	public void move() {
		div-= 0.2F;
		Vector dir = loc.getDirection();
		loc = s.getLocation();
		loc.setDirection(dir);
	
		s.setVelocity(loc.getDirection().multiply(div));
		for (Entity ent : hit) {
			ent.setVelocity(s.getVelocity());
		}
		
		if (s.isDead()) {
			dead = true;
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		if (refined)
			ParUtils.createParticle(Particle.FIREWORKS_SPARK, s.getLocation(), 0.1F, 0.1F, 0.1F, 5, 0.01F);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		tagPlayer(p);
		p.setVelocity(s.getVelocity());
		hit.add(p);
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 2, 2);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ent.setVelocity(s.getVelocity());
		hit.add(ent);
		SoundUtils.playSound(Sound.BLOCK_CONDUIT_ACTIVATE, loc, 2, 2);
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
		ParUtils.createParticle(Particle.FLASH, loc, 0, 0, 0, 1, 1);
		s.remove();
	}

}
