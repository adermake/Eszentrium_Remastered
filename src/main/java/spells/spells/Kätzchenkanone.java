package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Cat.Type;

import esze.utils.ParUtils;
import org.bukkit.Particle;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Explosion;
import spells.stagespells.ExplosionDamage;
import spells.stagespells.Repulsion;
import spells.stagespells.SelfRepulsion;

public class Kätzchenkanone extends Spell {

	Cat cat;

	public Kätzchenkanone() {

		name = "§6Kätzchenkanone";
		cooldown = 20 * 18;
		steprange = 70;
		hitSpell = true;
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		addSpellType(SpellType.DAMAGE);
		addSpellType(SpellType.MOBILITY);
		setLore("§7Wirft eine Katze in Blickrichtung, die#§7auf dem Boden stehen bleibt. Nach kurzer#§7Zeit explodiert diese und schadet alle#§7Gegner und schleudert sie weg.# #§eShift:§7#§7Sprengt die Katze vorzeitig. Wenn der Anwender#§7getroffen wird, schleudert ihn die#§7Explosion ebenfalls weg, verursacht aber keinen#§7Schaden.");
		setBetterLore("§7Wirft eine Katze in Blickrichtung, die#§7auf dem Boden stehen bleibt. Nach kurzer#§7Zeit explodiert diese und schadet alle Gegner#§7und schleudert sie weg.# #§eShift:§7#§7Sprengt die Katze vorzeitig. Wenn der Anwender#§7getroffen wird, schleudert ihn die Explosion#§7ebenfalls weg, verursacht aber keinen#§7Schaden.");
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		cat = (Cat) caster.getWorld().spawnEntity(caster.getEyeLocation().add(caster.getLocation().getDirection()), EntityType.CAT);
		cat.setBaby();
		cat.setSitting(true);

		cat.setVelocity(caster.getLocation().getDirection().multiply(3));
		
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

			onDeath();
			
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
				new ExplosionDamage(4, 8, caster, loc,name);
				new Repulsion(4, 6, caster, loc,false,name);
				new SelfRepulsion(8, 6, caster, loc,name);
			}
			else {
				new ExplosionDamage(4, 5, caster, loc,name);
				new Repulsion(4, 4, caster, loc,false,name);
				new SelfRepulsion(8, 4, caster, loc,name);
			}
			playSound(Sound.ENTITY_GENERIC_EXPLODE, caster.getLocation(), 3.0F, 1F);
			ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 4, 1);
			ParUtils.dropItemEffectRandomVector(loc, Material.TROPICAL_FISH, 1,40, 0.3);
			ParUtils.dropItemEffectRandomVector(loc, Material.RABBIT_HIDE, 1,40, 0.3);
		}
	}

	

}
