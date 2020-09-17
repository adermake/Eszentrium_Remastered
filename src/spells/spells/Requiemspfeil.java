package spells.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import spells.spellcore.Spell;

public class Requiemspfeil extends Spell {

	Location toLoc;
	Vector dir;
	Arrow a;
	public Requiemspfeil() {
		
		name = "§cRequiemspfeil";
		steprange = 150;
		cooldown = 20 * 50;
		hitboxSize = 1.5;
		speed = 1;
		hitSpell = true;
		
	}
	Location ori;
	Location point;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		a = (Arrow) spawnEntity(EntityType.ARROW,caster.getEyeLocation().add(caster.getLocation().getDirection()));
		
		dir = caster.getLocation().getDirection();
		ori = caster.getLocation();
		//caster.setGameMode(GameMode.SPECTATOR);
		caster.teleport(caster.getLocation().add(0,1,0));
		setGliding(caster, true);
		PlayerUtils.hidePlayer(caster);
		bindEntity(a);
		caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20000, 1,true));
		
		
		
			
			
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int speedX = 0;
	@Override
	public void move() {
		
		if (silenced.contains(caster)) {
			dead = true;
		}
		if (a.isOnGround()) {
			dead = true;
		}
		float f = ((float)speedX)/50;
		
		playGlobalSound(Sound.BLOCK_NOTE_BLOCK_FLUTE,0.3F,f);
		caster.setNoDamageTicks(3);
		// TODO Auto-generated method stub
		loc = a.getLocation();
		if (caster.isSneaking()) {
			speedX++;
			a.setVelocity(caster.getLocation().getDirection().multiply(1.5));
			
			//playSound(Sound.BLOCK_LAVA_EXTINGUISH,a.getLocation(),0.1F,0.4F);
			
		}
		else {
			a.setVelocity(caster.getLocation().getDirection().multiply(1));
			
			
		}
		
		
		if (speedX <= 0)
			speedX = 0;
		doPull(caster,a.getLocation(),a.getLocation().distance(caster.getLocation())/6);
		//Location aim = loc(caster,step);
		//doPull(a,aim,a.getLocation().distance(aim)/5);
		loc = a.getLocation();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		int color = ((int)speedX*2);
		if (color >255)
			color = 255;
		ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.fromBGR(color, color, 0), 1.5F);
	}

	@Override
	public void onPlayerHit(Player p) {
		playSound(Sound.ENTITY_ARROW_HIT_PLAYER,ori,5,1);
		playSound(Sound.ENTITY_ARROW_HIT_PLAYER,caster.getLocation(),5,1);
		// TODO Auto-generated method stub
		
			damage(p, speedX/5, caster);
		
		
		
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		//ParUtils.createParticle(Particles.EXPLOSION_EMITTER, toLoc, 0,0, 0, 1, 1); REMOVED CAUSED ERRORS
		playSound(Sound.ENTITY_ARROW_HIT_PLAYER,ori,5,1);
		playSound(Sound.ENTITY_ARROW_HIT_PLAYER,caster.getLocation(),5,1);
		damage(ent, speedX/5, caster);
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, 5);
		setGliding(originalCaster, false);
		originalCaster.setVelocity(new Vector(0,0,0));
		once = true;
		PlayerUtils.showPlayer(originalCaster);
		a.remove();
		originalCaster.removePotionEffect(PotionEffectType.INVISIBILITY);
		// TODO Auto-generated method stub
		originalCaster.teleport(ori);
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		dead = true;
	}

	boolean once = false;
	@Override
	public void onDeath() {
		if (!once) {
			ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, 5);
			setGliding(caster, false);
			caster.setVelocity(new Vector(0,0,0));
			once = true;
			PlayerUtils.showPlayer(caster);
			a.remove();
			caster.removePotionEffect(PotionEffectType.INVISIBILITY);
			// TODO Auto-generated method stub
			caster.teleport(ori);
			
		}
		
	}



}
