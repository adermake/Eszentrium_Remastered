package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
public class Flucht extends Spell{

	public Flucht() {
		
		
		steprange = 20*3;
		cooldown = 20*30;
		name = "§bFlucht";
		speed = 1;
		
		hitPlayer = false;
		hitBlock = false;
		
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.AURA);
		
		setLore("§7Verwandelt den Spieler für kurze Zeit#§7in eine Fledermaus und beschwört eine#§7Schwarm an Fledermäusen. Solange sich der#§7Spieler in dieserForm befindet, fliegt er in#§7Blickrichtung voraus.# #§eShift:§7 Beendet den#§7Zauber sofort.");
		setBetterLore("§7Verwandelt den Spieler für kurze Zeit#§7in eine Fledermaus und beschwört einen#§7Schwarm an Fledermäusen, die nahe Gegner wegschieben.#§7Solange sich der Spieler in dieser Form befindet,#§7fliegt er in Blickrichtung voraus.# #§eShift:§7 Beendet den#§7Zauber sofort.");
	}
	
	

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.hidePlayer(main.plugin, caster);
		}
		
		ParUtils.createParticle(Particles.LARGE_SMOKE, loc, 1, 1, 1, 100, 0);
		caster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 100));
		follow(bat(caster,refined),caster);
		for (int i = 0;i<35;i++) {
			bat(caster,refined);
		}
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		caster.setVelocity(caster.getLocation().getDirection().multiply(0.6));
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
		if (caster.getGameMode() != GameMode.ADVENTURE) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.showPlayer(main.plugin, caster);
			}
		}
		
	}
	
	public LivingEntity bat(final Player p,boolean refined) {
		
		final Bat bat = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
		unHittable.add(bat);
		
		
	
		new BukkitRunnable() {
			int t = 0;
			public void run() {
				t++;
				if (t>100) {
					this.cancel();
					bat.remove();
				}
				
				if (refined) {
					bat.setVelocity(bat.getLocation().getDirection());
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (caster == p)
							continue;
						
						if (p.getGameMode() == GameMode.SURVIVAL) {
							if (bat.getLocation().distance(p.getLocation())<2) {
								tagPlayer(p);
								p.setVelocity(bat.getVelocity());
							}
						}
					}
					
				}
			}
		}.runTaskTimer(main.plugin, 1,1);
		return bat;
	}
	public void follow (LivingEntity le,LivingEntity p) {
		new BukkitRunnable() {
			public void run() {
				
				if (caster.isSneaking()) {
					this.cancel();
					dead = true;
					le.remove();
				}
				if (le.isDead()) {
					this.cancel();
				}
				le.teleport(p);
				//doPull(le, p.getLocation(), 0.8D);
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}



}
