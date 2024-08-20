package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.ParUtils;
import esze.utils.Title;
import org.bukkit.Particle;
import spells.spellcore.Cooldowns;
import spells.spellcore.SilenceSelection;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Verstummen extends Spell {
	
	
	ArmorStand effect;
	
	public Verstummen() {
		name = "§eVerstummen";
		cooldown = 20 * 20;
		speed = 50;
		hitSpell = true;
		steprange = 300;
		hitboxSize = 2;
		
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.PROJECTILE);
		setLore("§7Schießt ein Projektil in Blickrichtung,#§7das getroffene Gegner für kurze Zeit#§7verstummt. Verstummte Gegner können keine#§7Zauber ausführen.");
		setBetterLore("§7Schießt ein Projektil in Blickrichtung ,#§7das getroffene Gegner für kurze Zeit#§7verstummt und den Spieler an diese Stelle#§7teleportiert. Verstummte Gegner können keine#§7Zauber ausführen.");
	}
	
	@Override
	public void setUp() {
		
		// TODO Auto-generated method stub
		effect = createArmorStand(caster.getLocation());
		effect.setHelmet(new ItemStack(Material.BARRIER));
		effect.setSmall(true);
		
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
		loc.add(loc.getDirection().multiply(0.5));
		for (int i = 0;i<5 ;i++) {
			ParUtils.chargeDot(loc, Particle.ENCHANTMENT_TABLE, 1, 3);
		}
		
		ParUtils.createParticle(Particle.WATER_BUBBLE, loc, 0.01, 0.01, 0.01, 1, 1);
		effect.teleport(loc);
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		damage(p,3,caster);
		playSound(Sound.ENTITY_ARROW_HIT_PLAYER,caster.getLocation(),5,1);
		// TODO Auto-generated method stub
		ParUtils.parKreisDir(Particle.END_ROD, loc, 2, 0,1, loc.getDirection().multiply(-1), loc.getDirection().multiply(-1));
		playSound (Sound.ENTITY_EVOKER_CAST_SPELL,p.getLocation(), (float) 1, 1);
	
		SilenceSelection s = new SilenceSelection();
		
		silence(p, s);
		new BukkitRunnable() {
			int t = 0;
			@Override
			public void run() {
				t++;
				for (int i = 0;i<5 ;i++) {
					ParUtils.chargeDot(p.getLocation().add(0,1,0), Particle.ENCHANTMENT_TABLE, 1, 3);
				}
			
				Title verstummt = new Title("§cVerstummt!","",0,2,0);
				verstummt.send(p);
				if (t>60) {
					Title nverstummt = new Title("","",0,2,0);
					nverstummt.send(p);
					silenced.remove(p);
					this.cancel();
					
				}
			}
		}.runTaskTimer(main.plugin, 1, 1);
		
		if (refined)
		caster.teleport(p.getLocation());
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
		effect.remove();
	}

	

}
