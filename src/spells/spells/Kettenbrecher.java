package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import esze.main.main;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.ChainSegment;

public class Kettenbrecher extends Spell {

	
	ArrayList<ChainSegment> segs = new ArrayList<ChainSegment>();
	ChainSegment lead;
	ChainSegment last;
	
	
	public Kettenbrecher() {
		name = "§cKettenbrecher";
		cooldown = 20 * 40;
		steprange = 20 * 6;
		hitEntity = false;
		hitSpell = false;
		hitPlayer = false;
		setLore("Beschwört eine Kette, die dem Mauszeiger hinterherfliegt. Getroffene Gegner werden in Flugrichtung der Kette weggeschleudert. Shift: Zieht die Kette an den Anwender heran. Sobald die Taste losgelassen wird, schnellt die Kette auf die Ursprungsentfernung zurück.");
		//setLore("Beschwört eine Kette, die dem Mauszeiger hinterherfliegt. Getroffene Gegner werden in Flugrichtung der Kette weggeschleudert. Shift: Zieht die Kette an den Anwender heran. Sobald die Taste losgelassen wird, schnellt die Kette auf die Ursprungsentfernung zurück.");
		addSpellType(SpellType.MULTIHIT);
		addSpellType(SpellType.SELFCAST);
		addSpellType(SpellType.PROJECTILE);
		addSpellType(SpellType.KNOCKBACK);
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		lead = new ChainSegment(caster, name, null,-1);
		lead.loc = caster.getLocation();
		last = lead;
	
		
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	int segmentCount = 120;
	//double maxlength = 20;
	//double length = 20;
	@Override
	public void move() {
		if (segmentCount > 0) {
			for (int i = 0;i<25;i++) {
				segmentCount--;
				ChainSegment c = new ChainSegment(caster, name, last,segmentCount);
				segs.add(c);
				last = c;
			}
			
		}
		// TODO Auto-generated method stub
		for (ChainSegment seg : segs) {
			seg.vel.add(new Vector(0,-0.001F,0));
		}
	
		if (silenced.containsKey(caster)) {
			dead = true;
		}
		//lead.vel = caster.getLocation().add(caster.getLocation().getDirection().multiply(10)).toVector().subtract(loc.toVector()).normalize();
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
		
		new BukkitRunnable() {
			public void run() {
				for (int i = 0;i<20;i++) {
					
				
				if (segs.size() <= 0) {
					this.cancel();
					
					lead.kill();
					break;
					
				}
				else  {
					segs.get(segs.size()-1).kill();
					segs.remove(segs.size()-1);
				}
				}
			}
		}.runTaskTimer(main.plugin,1,1);
	}

}
