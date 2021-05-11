package spells.spells;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.NoCollision;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Bienenschwarm extends Spell {

	public Bienenschwarm() {
		cooldown = 20 * 25;
		name = "§6Bienenschwarm";
		speed = 1.2;
		steprange =82;
		hitboxSize = 2;
		hitPlayer = true;
		hitSpell = true;
		addSpellType(SpellType.PROJECTILE);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.LOCKDOWN);
		setLore("§7Schießt einen Honigklumpen in Blickrichtung, der#§7bei Kontakt mit Gegnern Bienen anlockt. Diese halten#§7den Gegner für kurze Zeit in der Luftund fügen ihm#§7Schaden zu. Der Honigklumpen kann selbst nach der#§7Ausführung noch gesteuert werden.");
		setBetterLore("§7Schießt einen Honigklumpen in Blickrichtung, der#§7bei Kontakt mit Gegnern eine Menge Bienen anlockt.#§7Diese halten den Gegner für kurze Zeit in der Luft und#§7fügen ihm Schaden zu. Der Honigklumpen kann selbst#§7nach der Ausführung noch gesteuert werden.");
	}
	public Bienenschwarm(Player c,String n) {
		super();
		cooldown = 5;
		caster = c;
		speed = 1.2;
		hitboxSize = 2;
		steprange =82;
		hitPlayer = true;
		hitSpell = true;
		loc = c.getEyeLocation();
		castSpell(c, n);
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		if (refined) {
			new BukkitRunnable() {
				int t = 0;
				@Override
				public void run() {
					t++;
					if (t > 1) {
						this.cancel();
					}
					new Bienenschwarm(caster,name);
				}
			}.runTaskTimer(main.plugin, 10, 10);
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		loc.add(loc.getDirection().multiply(0.5));
		playSound(Sound.BLOCK_HONEY_BLOCK_SLIDE,loc,1f,1f);
		loc.setDirection(caster.getLocation().getDirection());
	}

	@Override
	public void display() {
		ParUtils.createFlyingParticle(Particles.LANDING_HONEY, loc, 0, 0, 0, 1, 1, loc.getDirection().multiply(1));
		ParUtils.createBlockcrackParticle(loc, 0.1F, 0.1F, 0.1F, 4, Material.HONEY_BLOCK);
		
	}

	@Override
	public void onPlayerHit(Player p) {
	
		p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1,true));
		playSound(Sound.ENTITY_ELDER_GUARDIAN_FLOP, p.getLocation(), 101, 1.5F);
		dead = true;
		hit(p);
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		playSound(Sound.ENTITY_ELDER_GUARDIAN_FLOP, ent.getLocation(), 101, 1.5F);
		ent.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1,true));
		
		dead = true;
		hit(ent);
		
	}
	
	public void hit(LivingEntity e) {
		ParUtils.createBlockcrackParticle(e.getLocation().clone().add(0,0.5,0), 0.2F, 0.4F, 0.2F, 40, Material.HONEY_BLOCK);
		playSound(Sound.BLOCK_HONEY_BLOCK_BREAK,loc,1f,1f);
		ArrayList<Bee> bees = new ArrayList<Bee>();
		for (int i = 0;i<10;i++) {
			Bee b = (Bee) spawnEntity(EntityType.BEE, e.getLocation(),200);
			b.setBaby();
			
			b.setVelocity(randVector().multiply(0.4));
			NoCollision.dontCollide(b);
			NoCollision.dontCollide(e);
			unHittable.add(b);
			noTargetEntitys.add(b);
			if (e instanceof LivingEntity) {
				((LivingEntity)e).setCollidable(false);
			}
			if (e instanceof Player) {
				((Player)e).setCollidable(false);
			}
			b.setCollidable(false);
			
			bees.add(b);
		}
		
		
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				for (Bee b : bees) {
					Vector v = e.getEyeLocation().toVector().subtract(b.getLocation().toVector()).normalize().multiply(0.5);
					b.setVelocity(b.getVelocity().add(v));
				}
				t++;
				
				ParUtils.createParticle(Particles.FALLING_HONEY, e.getLocation(), 0.2, 1, 0.2, 10, 1);
				if (t > 20) {
					
					
					for (Bee b : bees) {
						Vector v = e.getEyeLocation().toVector().subtract(b.getLocation().toVector()).normalize();
						ParUtils.parLine(Particles.CRIT, b.getLocation(), b.getLocation().add(v.multiply(5)), 0, 0, 0, 1, 0.01F, 0.2F);
						damage(e, 4,caster);
						playSound(Sound.ENTITY_BEE_STING,loc,1f,2f);
						b.remove();
					}
					
					this.cancel();
				}
				
			}
		}.runTaskTimer(main.plugin, 1,5);
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		dead = true;
		//bounce();
		
	}


	@Override
	public void onDeath() {
		
		
	}


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}



}
