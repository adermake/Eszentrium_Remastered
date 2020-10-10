package spells.spells;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Eisstachel extends Spell {

	public Eisstachel() {
		cooldown = 20*40;
		name = "§eEisstachel";
		speed = 10;
		steprange =32;
		hitPlayer = true;
		hitSpell = true;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.PROJECTILE);
		
		setLore("§7Schießt einen Eiszapfen in#§7Blickrichtung. Bei Gegnerkontakt wird dieser vereist#§7und an der Stelle für kurze Zeit#§7festegehalten.");
		setBetterLore("§7Schießt einen Eiszapfen in#§7Blickrichtung. Bei Gegnerkontakt wird dieser vereist#§7und an der Stelle für kurze Zeit#§7festegehalten.");
	}
	Location saveLoc = null;
	boolean noSpikes = false;
	public Eisstachel(Location l,Vector dir,Player caster) {
		super();
		cooldown = 20*40;
		name = "§eEisstachel";
		speed = 10;
		steprange =32;
		hitPlayer = true;
		hitSpell = true;
		hitboxSize = 3;
		refined = false;
		noSpikes = true;
		saveLoc = l.clone().setDirection(dir);
		castSpell(caster, name);
		
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		//ParUtils.parKreisDir(Particles.CLOUD, loc, 3, 2, c, caster.getLocation().getDirection(), caster.getLocation().getDirection());
		if (saveLoc != null)	
		loc = saveLoc;
			
	}
	Vector last;
	int c = 0;
	@Override
	public void cast() {
		c++;
		
		/*
		Location l = ParUtils.stepCalcCircle(caster.getEyeLocation(), 3, caster.getLocation().getDirection(), 3, c*4);
		
		
		
		if (last == null) {
			last = l.toVector();
		}
		Vector d = l.toVector().subtract(last);
		ParUtils.createFlyingParticle(Particles.CLOUD, l, 0, 0, 0, 1, 1, d.normalize().multiply(0.1F));
		
		for (int i = 0;i<2;i++) {
			Location l1 = ParUtils.stepCalcCircle(caster.getEyeLocation(), 3, caster.getLocation().getDirection(), 3, c*i);
			ParUtils.createFlyingParticle(Particles.CLOUD, l1, 0, 0, 0, 1, 1, d.normalize().multiply(0.1F));
		}
		last = l.toVector();
		*/
	}

	@Override
	public void move() {
		loc.add(loc.getDirection().multiply(0.8));
		playSound(Sound.ENTITY_GENERIC_EXPLODE,loc,0.1f,1f);
	}

	int i = 0;
	int l = 10;
	ArrayList<FallingBlock> blocks = new ArrayList<FallingBlock>();
	@Override
	public void display() {
		
		float fstep = (float) step;
		float fsteprange = steprange;
		float lFactor = fstep/fsteprange;
		i++;
		l--;
		if (l < 3) {
			l = 3;
		}
		if (i % 4 == 0 && !noSpikes) {
			
			Vector v = caster.getLocation().getDirection();
			v = v.multiply(3).add(randVector().multiply(1-lFactor)).normalize();
			new Spike(caster,v,name,loc.clone(),l*3);
		}
		if (!loc.getBlock().getType().isSolid()) {
			FallingBlock fb = caster.getWorld().spawnFallingBlock(loc, Material.ICE,(byte) 0);
			fb.setGravity(false);
			blocks.add(fb);
		}
			
		
		// TODO Auto-generated method stub
		//ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));
		ParUtils.createParticle(Particles.CLOUD, loc, 0.1, 0.1, 0.1, 3, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
		
		damage(p, 5,caster);
		
		onHitEffect(p);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 5,caster);
		
		
		onHitEffect(ent);
		
		dead = true;
	}

	public void onHitEffect(LivingEntity ent) {
		
		if (refined) {
			for (int i = 0;i<4;i++) {
				Vector rand = randVector().multiply(1).add(loc.getDirection().multiply(-3).normalize());
				if (rand.getY()>0.1) {
					rand.setY(0.1);
				}
				if (rand.getY()<0.1) {
					rand.setY(-0.1);
				}
				//new Spike(caster,loc.getDirection().multiply(-1).add(randVector().multiply(3)),name,ent.getLocation(),randInt(1,14),90);
				new Eisstachel(ent.getLocation().add(rand), rand, caster);
			}
		}
		else {
			for (int i = 0;i<20;i++) {
				//randVector().multiply(1).add(loc.getDirection().multiply(-3).normalize())
				new Spike(caster,loc.getDirection().multiply(-1).add(randVector().multiply(3)),name,ent.getLocation(),randInt(1,14),90);
			}
		}
		
		
		new BukkitRunnable() {
			Location l = ent.getLocation();
			int t = 0;
			@Override
			public void run() {
				t++;
				if (t > 20 * 5) {
					this.cancel();
				}
				if (ent instanceof Player) {
					Player p = (Player)ent;
					if (p.getGameMode() == GameMode.ADVENTURE ) {
						this.cancel();
					}
				}
				doPin(ent, l);
			}
		}.runTaskTimer(main.plugin, 1, 1);
	}
	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		
		
		//bounce();
		
	}


	@Override
	public void onDeath() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (FallingBlock fb : blocks) {
					ParUtils.createBlockcrackParticle(fb.getLocation(), 0.1F, 0.1F, 0.1F, 4, Material.PACKED_ICE);
					playSound(Sound.BLOCK_GLASS_BREAK,fb.getLocation(),1f,2f);
					fb.remove();
				}
				
			}
		}.runTaskLater(main.plugin, 40);
		
	}


	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}


}
