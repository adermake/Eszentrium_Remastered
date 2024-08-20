package spells.spells;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.PullRanke;

public class Ranke extends Spell{
	HashMap<Integer,Vector> path = new HashMap<Integer,Vector>();
	HashMap<Integer,FallingBlock> blocks = new HashMap<Integer,FallingBlock>();
	FallingBlock current;
	public Ranke() {
		
		name = "§eRanke";
		steprange = 200;
		hitboxSize = 1.5;
		powerlevel = 60;
		speed = 3;
		
		cooldown = 20 * 18;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Schießt eine Ranke in Blickrichtung.#§7Ein getroffener Gegner wird zum Anwender#§7gezogen und erhält Schaden abhängig von der#§7Länge der Ranke.Die Ranke kann selbst nach#§7der Ausführung noch gesteuert werden.");
		setBetterLore("§7Schießt eine Ranke in Blickrichtung. Ein#§7getroffener Gegner wird bis auf die#§7maximale Reichweite und danach zurück zum#§7Anwender gezogen.Die Ranke kann selbst nach der#§7Ausführung noch gesteuert werden.");
	}
	public Ranke(Player p,int rec) {
		
		super();
		name = "§cRanke";
		steprange = 150;
		hitboxSize = 1.5;
		powerlevel = 60;
		speed = 3;
		caster = p;
		cooldown = 20 * 20;
		castSpell(caster, name);
		if (rec > 0) {
			new BukkitRunnable() {
				public void run() {
					new Ranke(caster,rec-1);
				}
			}.runTaskLater(main.plugin, 10);
			
			
		}
	}
	
	
	
	@Override
	public void cast() {
		
		
	}
	
	@Override
	public void setUp() {
		loc = caster.getEyeLocation();
		
	
	}
	
	@Override
	public void move() {
		if (loc.getY() < 65) {
			dead = true;
		}
		playSound(Sound.ENTITY_LEASH_KNOT_PLACE, loc, 5, 2);
		Vector v = caster.getLocation().getDirection();
		path.put((int)step, loc.toVector());
		loc.add(v.normalize().multiply(0.5));
		FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, Material.JUNGLE_LEAVES, (byte)0);
		fb.setGravity(false);
		current = fb;
		blocks.put((int)step, fb);
		if (step == steprange-1) {
			fb.remove();
			new PullRanke(caster, null, path, blocks, loc, (int) step,name);
		}
		
		if (refined) {
			for (LivingEntity le : hitMobs) {
				//le.addPotionEffect(new PotionEffect(PotionEffectType.POISON,20,6));
				doPin(le, loc.clone());
			}
		}
	}
	
	
	


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	ArrayList<LivingEntity> hitMobs = new ArrayList<LivingEntity>();
	@Override
	public void onPlayerHit(Player p) {
		
		if (refined) {
			hitMobs.add(p);
		}
		else {

			
			current.remove();
			new PullRanke(caster, p, path, blocks, loc, (int) step,name);
			damage(p, 3, caster);
			
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (int)step/3, 1));
			dead = true;
		}
		
	}


	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		if (refined) {
			hitMobs.add(ent);
		}
		else {
		current.remove();
		new PullRanke(caster, ent, path, blocks, loc, (int) step,name);
		damage(ent, 3, caster);
		dead = true;
		}
	}


	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
	
		current.remove();
		
		new PullRanke(caster, null, path, blocks, loc, (int) step,name);
		dead = true;
	}


	@Override
	public void onDeath() {
		if (refined) {
			for (LivingEntity le : hitMobs) {
				new PullRanke(caster, le, path, blocks, loc, (int) step,name);
				
				
			}
		}
		
		new BukkitRunnable() {
			public void run() {
				for (FallingBlock fb : blocks.values()) {
					if (fb != null) {
						fb.remove();
					}
				}
			}
		}.runTaskLater(main.plugin, 100);
	}

}
