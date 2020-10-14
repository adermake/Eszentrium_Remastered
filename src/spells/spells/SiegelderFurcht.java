package spells.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.google.common.collect.ImmutableList;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class SiegelderFurcht extends Spell {

	
	public SiegelderFurcht() {
		name = "§3Siegel der Furcht";
		cooldown = 20 * 30;
		hitboxSize = 8;
		hitEntity = true;
		hitSpell = true;
		steprange = 90;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.AURA);
		
		setLore("§7Versetzt alle naheliegenden Gegner in#§7Panik, wodurch sie für kurze Zeit#§7automatisch vor dem Anwender fliehen. Für diese Zeit#§7erhältder Anwender eine erhöhte#§7Bewegungsgeschwindigkeit.");
		setBetterLore("§7Versetzt alle naheliegenden Gegner in#§7Panik, wodurch sie für kurze Zeit automatisch#§7vor dem Anwender fliehen. Für diese Zeit#§7erhältder Anwender eine erhöhte#§7Bewegungsgeschwindigkeit.# #§eF:§7 Solange diese Taste#§7gedrückt bleibt bewegen sich in Panik#§7versetzte Gegner auf den Anwender zu.");
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		Firework firework = (Firework) caster.getWorld().spawnEntity(caster.getLocation().add(0,7,0), EntityType.FIREWORK);
	
		
		
		

	       

	        FireworkMeta fd = (FireworkMeta) firework.getFireworkMeta();

	        fd.addEffect(FireworkEffect.builder()

	                .flicker(false)

	                .trail(true)

	                .with(Type.BALL)

	                .with(Type.BALL_LARGE)

	                .with(Type.CREEPER)

	                .withColor(Color.PURPLE)
	                .withColor(Color.BLACK)
	               

	                .withFade(Color.BLACK)

	                

	                .build());

	        firework.setFireworkMeta(fd);
		firework.detonate();
		
		ParUtils.parKreisDir(Particles.LARGE_SMOKE, caster.getLocation(), 4, 0, 0.7, new Vector(0,1,0),new Vector(0,1,0));
		playSound(Sound.ENTITY_ENDER_DRAGON_GROWL,caster.getLocation(),10,0.4F);
		caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20* 5, 2));
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (step>4) {
			hitPlayer = false;
			hitEntity = false;
			hitSpell = false;
		}else {
			ParUtils.parKreisDir(Particles.LARGE_SMOKE, caster.getLocation(), 4+step*2, 0, 0.7, new Vector(0,1,0),new Vector(0,1,0));
		}
		
		for (Entity ent : hitEntitys) {
			Location fromLocation = caster.getLocation();
			double Ax = fromLocation.getX();
			double Ay = fromLocation.getY();
			double Az = fromLocation.getZ();

			double Bx = ent.getLocation().getX();
			double By = ent.getLocation().getY();
			double Bz = ent.getLocation().getZ();

			double x = Bx - Ax;
			double y = 0;
			double z = Bz - Az;
			
			Vector v = new Vector(x, y, z).normalize().multiply(0.5);
			if (refined && caster.isSneaking()) {
				ent.setVelocity(ent.getVelocity().add(v).normalize().multiply(-0.3).setY(ent.getVelocity().getY()));
			}else {
				ent.setVelocity(ent.getVelocity().add(v).normalize().multiply(0.3).setY(ent.getVelocity().getY()));
			}
			
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		tagPlayer(p);
		p.teleport(lookAt(p.getLocation(),caster.getLocation().add(0,2,0)));
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		ent.teleport(lookAt(ent.getLocation(),caster.getLocation().add(0,2,0)));
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
