package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Erdsurfer extends Spell {
	public Erdsurfer() {
		cooldown = 20*60;
		name = "§bErdsurfer";
		speed = 1;
		steprange = 0;
		hitPlayer = false;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.MOBILITY);
		
		setLore("§7Reitet auf der Erde, um sich in#§7Blickrichtung fortzubewegen und zieht Blöcke#§7hinter sich her. Gegner können von diesen#§7Blöcken erfasstwerden und werden danach#§7ebenfalls mitgezogen.# #§eShift:§7 Wirft allen#§7aufgesammelten Ballast in Blickrichtung weg und#§7wirft den Anwender nach hinten.");
		setBetterLore("§7Reitet auf der Erde, um sich in#§7Blickrichtung fortzubewegen und zieht Blöcke hinter#§7sich her. Gegner können von diesen Blöcken#§7erfasstwerden und werden danach ebenfalls#§7mitgezogen.# #§eShift:§7 Wirft allen#§7aufgesammelten Ballast in Blickrichtung weg und#§7wirft den Anwender nach hinten.");
		
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
		caster.setVelocity(caster.getVelocity().setY(1.5));

	}

	int t = 0;
	@Override
	public void move() {
		if (dead)
			return;
		t++;
		caster.setAllowFlight(true);
		caster.setFlying(true);
		loc.add(randInt(-3, 3), 0, randInt(-3, 3));
		playSound(Sound.BLOCK_WATER_AMBIENT, caster.getLocation(), 1, 1);
		int ab = 0;
		int ti = 0;
		while (loc.getBlock().getType() == Material.AIR
				|| loc.clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
			ab++;
			loc.add(0, -1, 0);
			ti++;
			if (ti > 200) {
				break;
			}
		}

		final FallingBlock fb = caster.getWorld().spawnFallingBlock(loc.clone().add(0, 1, 0), loc.getBlock().getType(),
				(byte) 0);
		final FallingBlock binder = caster.getWorld().spawnFallingBlock(loc.clone().add(0, 1, 0), loc.getBlock().getType(),
				(byte) 0);

		doPull(fb, caster.getLocation(), 1);
		if (refined) {
			doPull(binder, caster.getLocation(), 3);
		}
		else {
			doPull(binder, caster.getLocation(), 1);
		}
		
		new BukkitRunnable() {
			int ti = 0;

			public void run() {
				ti++;
				doPull(binder, caster.getLocation(), 1);
				if (binder.getLocation().distance(caster.getLocation()) < 2) {
					binder.remove();
				}
				for (LivingEntity le : caster.getWorld().getLivingEntities()) {
					if (checkHit(le, loc, caster, 4)) {

						new BukkitRunnable() {
							int time = 0;

							public void run() {
								time++;
								if (!dead)
								doPull(le, caster.getLocation().clone().add(0, -2, 0), 1);
								if (time < 30) {
									this.cancel();
								}
								if (t > 70 || caster.isSneaking()) {
									le.setVelocity(caster.getLocation().getDirection().multiply(2));
									this.cancel();
								}

							}
						}.runTaskTimer(main.plugin, 1, 1);

						if (le.getLocation().distance(binder.getLocation()) < 2) {
							new BukkitRunnable() {
								int time = 0;

								public void run() {
									time++;
									doPull(le, caster.getLocation().clone().add(0, -2, 0), 1);
									if (time < 30) {
										this.cancel();
									}
									if (t > 70 || caster.isSneaking() || caster.getGameMode() == GameMode.ADVENTURE) {
										le.setVelocity(caster.getLocation().getDirection().multiply(2));
										this.cancel();
									}

								}
							}.runTaskTimer(main.plugin, 1, 1);
						}
					}
				}

				if (refined) {
					doPull(fb, caster.getLocation(), 2);
				}
				else {
					doPull(fb, caster.getLocation(), 1);
				}
				
				loc = caster.getLocation();
				if (ti > 30) {
					binder.remove();
					fb.remove();
					this.cancel();
				}
				if (t > 70 || caster.isSneaking()) {
					this.cancel();
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
		if (ab > 7) {
			caster.setAllowFlight(false);
			caster.setFlying(false);
		} else {
			caster.setVelocity(caster.getVelocity().setY(0.5));
			caster.setAllowFlight(true);
			caster.setFlying(true);
		}
		Vector v = caster.getLocation().getDirection();
		v.setY(0);
		if (refined) {
			caster.setVelocity(v.multiply(1.5F));
		}
		else {
			caster.setVelocity(v);
		}
		

		if (t > 70 || caster.isSneaking()) {

			caster.setFlying(false);
			caster.setAllowFlight(false);
			if (refined) {
				caster.setVelocity(caster.getLocation().getDirection().multiply(-3).add(new Vector(0,1.5,0)));
			}
			else {
				caster.setVelocity(caster.getLocation().getDirection().multiply(-1));
			}
			
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!pl.getName().equals(caster.getName()) && pl.getGameMode() != GameMode.ADVENTURE) {
					if (pl.getLocation().distance(caster.getLocation()) < 4) {
						if (refined) {
							pl.setVelocity(pl.getLocation().getDirection().multiply(6).add(new Vector(0,1.5,0)));
						}
						else {
							pl.setVelocity(pl.getLocation().getDirection().multiply(2));
						}
						
					}
				}
			}
			dead = true;
			return;
		}

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
		if (spell.getName().contains("Antlitz der Göttin")){
			caster.setFlying(false);
			caster.setAllowFlight(false);
		}
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
