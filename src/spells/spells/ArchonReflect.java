package spells.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class ArchonReflect extends Spell{

	public static ArrayList<Player> deflect = new ArrayList<Player>();
	public ArchonReflect(Player caster,String name) {
		name = "§rArchon Aura";
		hitSpell = true;
		hitPlayer = false;
		hitBlock = false;
		hitEntity = false;
	
		cooldown = 20*40;
		speed = 1;
		hitboxSize = 4;
		addSpellType(SpellType.AURA);
		castSpell(caster, name);
	}
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		deflect.remove(caster);
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		SoundUtils.playSound(Sound.ENTITY_WITCH_AMBIENT, loc, 3, 0.3F);
		deflect.add(caster);
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
		loc = caster.getLocation();
		if (refined)
			caster.setNoDamageTicks(5);
	}

	@Override
	public void display() {
		//playSound(Sound.BLOCK_NOTE_BLOCK_COW_BELL, loc, 10F, 0.1F);
		caster.setAllowFlight((true));
		loc = caster.getLocation();
		
		//ParUtils.createParticle(Particle.FLAME, dot, 0, 1, 0, 0, 14);
	
		
		//ParUtils.createParticle(Particle.VILLAGER_ANGRY, caster.getEyeLocation().add(0,-1.7,0), 0, 1, 0, 0, 1);
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
		spell.caster = caster;
		playSound(Sound.BLOCK_ENCHANTMENT_TABLE_USE, loc, 5, 2);
		spell.loc.setDirection(loc.getDirection());
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}
	
}
