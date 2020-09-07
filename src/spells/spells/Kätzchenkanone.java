package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Cat.Type;

import esze.utils.ParUtils;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.stagespells.Explosion;
import spells.stagespells.Repulsion;

public class Kätzchenkanone extends Spell {

	Cat cat;

	public Kätzchenkanone() {

		name = "§6Kätzchenkanone";
		cooldown = 20 * 20;
		steprange = 70;
		hitSpell = true;
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		cat = (Cat) caster.getWorld().spawnEntity(caster.getEyeLocation(), EntityType.CAT);
		cat.setBaby();
		cat.setSitting(true);

		cat.setVelocity(caster.getLocation().getDirection().multiply(2));
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub

	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_CAT_AMBIENT, caster.getLocation(), 3.0F, 1F);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

		if (caster.isSneaking()) {

			dead = true;
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
		if (spell.getName().contains("Antlitz der Göttin"))
			cat.setVelocity(spell.caster.getLocation().getDirection().multiply(2));
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeath() {
		if (cat != null && !cat.isDead()) {

			loc = cat.getLocation();

			playSound(Sound.ENTITY_CAT_DEATH, caster.getLocation(), 1, 1);
			cat.remove();
			if (loc.distance(caster.getLocation()) < 3) {
				doKnockback(caster, loc, 4);
			}
			playSound(Sound.ENTITY_CAT_DEATH, caster.getLocation(), 3.0F, 1F);
			if (refined) {
				new Explosion(1, 15, 0, 1, caster, loc,name);
				new Repulsion(3, 6, caster, loc,name);
			}
			else {
				new Explosion(1, 10, 0, 1, caster, loc,name);
				new Repulsion(3, 4, caster, loc,name);
			}
			
			
			ParUtils.dropItemEffectRandomVector(loc, Material.TROPICAL_FISH, 1,40, 0.3);
			ParUtils.dropItemEffectRandomVector(loc, Material.RABBIT_HIDE, 1,40, 0.3);
		}
	}

	

}
