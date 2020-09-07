package spells.stagespells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;

public class BowArrow extends Spell {

	Location origin;
	public BowArrow(Player p,Location origin, String namae) {
		name = namae;
		hitEntity = true;
		hitPlayer = true;
		hitSpell = true;
		steprange = 160;
		speed = 12;
		hitboxSize = 1;
		this.origin = origin;
		
		castSpell(p, name);
	}
	Arrow a;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		a = (Arrow)spawnEntity(EntityType.ARROW,caster.getEyeLocation().add(caster.getLocation().getDirection()));
		a.setKnockbackStrength(0);
		a.setDamage(0);
		a.setVelocity(loc.getDirection().multiply(speed));
		loc = origin;
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		SoundUtils.playSound(Sound.ENTITY_ARROW_SHOOT, origin,1,5);
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.add(loc.getDirection().multiply(0.5));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		a.setVelocity(loc.getDirection().multiply(speed/2));
		a.setPierceLevel(100);
		if (step > 10)
		ParUtils.createParticle(Particles.CRIT, loc, 0.05F, 0.05F, 0.05F, 1, 0);
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		damage(p, 6, caster);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		damage(ent, 6, caster);
		dead = true;
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		dead = true;
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		ParUtils.createParticle(Particles.CLOUD, loc, 0.05F, 0.05F, 0.05F, 5, 0.3F);
		if (a != null)
		a.remove();
	}

}
