package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.enums.GameType;
import esze.types.Type;
import esze.utils.ParUtils;
import esze.utils.TTTCorpse;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Wurmloch extends Spell {

	public Wurmloch() {
		name = "§bWurmloch";
		cooldown = 20 * 25;
		canHitSelf = true;
		hitboxSize = 6;
		hitPlayer = true;
		hitEntity = true;
		hitSpell = false;
		steprange = 120;
		addSpellType(SpellType.SUPPORT);
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.AURA);
		setLore("§7Kreiiert ein Wurmloch über dem Abgrund#§7in Blickrichtung. Springt ein Spieler#§7durch dieses Wurmloch, wird er wieder zu einem#§7Spawnpunktteleportiert.");
		setBetterLore("§7Kreiiert ein Wurmloch über dem Abgrund#§7in Blickrichtung. Springt ein Spieler durch#§7dieses Wurmloch, wird er wieder zu einem#§7Spawnpunktteleportiert und wird außerdem voll#§7geheilt.");
	}
	
	Location wormHolePlaceLoc;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		wormHolePlaceLoc = loc(caster,(int)(caster.getLocation().getY()-30));
		wormHolePlaceLoc.setY(62);
		playSound(Sound.BLOCK_PORTAL_TRAVEL, caster.getLocation(), 3, 2F);
		playSound(Sound.BLOCK_PORTAL_TRAVEL, wormHolePlaceLoc, 3, 2F);
		
		for (TTTCorpse c : TTTCorpse.getCorpses(loc, 6)) {
		
			c.teleport(GameType.getType().nextLoc());
		}
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		loc = wormHolePlaceLoc.clone();
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	int delay = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		delay++;
		if (delay > 5) {
			delay = 0;
			ParUtils.parKreisDir(Particles.DRAGON_BREATH, loc.clone().add(0,16,0), 6, 0, 2, new Vector(0,1,0), new Vector(0,-1,0));
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_ENDERMAN_TELEPORT, p.getLocation(), 3, 0.2F);
		ParUtils.createRedstoneParticle(p.getLocation(), 0, 0, 0, 1, Color.PURPLE, 15);
		p.teleport(GameType.getType().nextLoc());	
		if (refined) {
			p.setHealth(p.getMaxHealth());
			ParUtils.createParticle(Particles.HEART, p.getEyeLocation(), 1, 1, 1, 5, 1);
		}
		ParUtils.createRedstoneParticle(p.getLocation(), 0, 0, 0, 1, Color.PURPLE, 15);
		playSound(Sound.ENTITY_ENDERMAN_TELEPORT, p.getLocation(), 3, 0.2F);
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
		
	}


}
