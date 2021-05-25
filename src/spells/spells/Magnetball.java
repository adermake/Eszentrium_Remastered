package spells.spells;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Magnetball extends Spell{

	ArrayList<ArmorStand> stands = new ArrayList<ArmorStand>();
	public Magnetball() {
		name = "�eMagnetball";
		cooldown = 20 * 22;
		hitEntity = true;
		hitSpell = true;
		hitPlayer = true;
		hitboxSize = 10;
		steprange = 150;
		speed = 5;
		multihit = true;
		
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("�7Schie�t ein Projektil in#�7Blickrichtung. Gegner in der N�he werden markiert,#�7solange sie sich in der Reichweite befinden.##�7#�eShift:�7 Zieht markierte Gegner heran und#�7schleudert sie weg.");
		setBetterLore("�7Schie�t ein Projektil in#�7Blickrichtung. Gegner in der N�he werden markiert,#�7solange sie sich in der Reichweite befinden.##�7#�eShift:�7 Zieht markierte Gegner heran und#�7schleudert sie weg.");
	
		
		
		
		
		
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		for (int i = 0;i<5;i++) {
			ArmorStand a = createArmorStand(caster.getLocation());
			if (refined) {
				a.getEquipment().setHelmet(new ItemStack(Material.GOLD_BLOCK));
			}
			else {
				a.getEquipment().setHelmet(new ItemStack(Material.IRON_BLOCK));
			}
			
			a.setGravity(true);
			a.setMarker(false);
			stands.add(a);
		}
		if (refined) {
			hitboxSize = 14;
		}
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
		if (dead)
			return;
		// TODO Auto-generated method stub
		
		if (caster.isSneaking()) {
			
			onDeath();
			return;
			
		}
		loc.add(loc.getDirection().multiply(0.5));
		for (ArmorStand a : stands) {
			a.teleport(loc);
		}
		playSound(Sound.BLOCK_CONDUIT_ATTACK_TARGET,loc,1f,2f);
	
			
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0, 0,0, 1, 4, loc.getDirection());
		if (step % 2 == 0)
		ParUtils.createParticle(Particles.CLOUD, loc, 0.1, 0.1, 0.1, 1, 0.2);
		
		for (ArmorStand a : stands) {
			double randVal = 0.1;
			a.setHeadPose(a.getHeadPose().add(randDouble(-randVal,randVal), randDouble(-randVal,randVal), randDouble(-randVal,randVal)));
		}
	}

	@Override
	public void onPlayerHit(Player p) {
		double distance = p.getLocation().distance(loc);
		
		ParUtils.parLine(Particles.BUBBLE, loc.clone(),  p.getLocation().add(0,0.5,0), 0, 0, 0, 1, 0, 0.4);
		//if (step< steprange-10)
			
		//ParUtils.parLineRedstone(loc, p.getLocation().add(0,0.5,0), Color.fromBGR(0, clamp(200-(int)distance*15, 0, 255),clamp((int) (100+distance*15), 0, 255)), 1, 0.5);
		
	}
	
	public int clamp(int i, int min, int max) {
		return (i < min) ? min : ((i > max) ? max : i);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		double distance = ent.getLocation().distance(loc);
		ParUtils.parLine(Particles.BUBBLE, loc.clone(),  ent.getLocation().add(0,0.5,0), 0, 0, 0, 1, 0, 0.4);
		/*
		if (step< steprange-10)
		ParUtils.parLineRedstone(loc, ent.getLocation().add(0,0.5,0), Color.fromBGR(0, clamp(200-(int)distance*15, 0, 255),clamp( (int) (100+distance*15),0 ,255 )), 1, 0.5);
		*/
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
		if (dead)
			return;
		
		dead = true;
		// TODO Auto-generated method stub
		ParUtils.chargeDot(loc, Particles.END_ROD, 0.2, 4,60);
		playSound(Sound.ENTITY_WITHER_SPAWN,loc,4f,2f);
		for (Entity ent : hitEntitys) {
			LivingEntity e = (LivingEntity) ent;
			if (e.getEyeLocation().distance(loc)<0.6+hitboxSize || e.getLocation().distance(loc)<0.6+hitboxSize ) {
				if (e instanceof Player) {
					tagPlayer((Player) e);
				}
				if (refined) {
					e.setVelocity(loc.toVector().subtract(e.getLocation().toVector()).normalize().multiply(5));
				}
				else {
					e.setVelocity(loc.toVector().subtract(e.getLocation().toVector()).normalize().multiply(3));
				}
				
			}
		}
		
		for (ArmorStand a : stands) {
			a.remove();
		}
		ParUtils.createParticle(Particles.FLASH, loc,0, 0, 0, 1, 1);
	}
	

}
