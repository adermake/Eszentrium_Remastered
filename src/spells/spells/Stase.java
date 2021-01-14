package spells.spells;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import net.minecraft.server.v1_15_R1.Particles;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.VelocityTimeStop;

public class Stase extends Spell {

	
	public Stase() {
		name = "§cStase";
		cooldown = 20*50;
		speed = 2F;
		steprange = 40;
		hitboxSize = 1.4;
		addSpellType(SpellType.LOCKDOWN);
		addSpellType(SpellType.KNOCKBACK);
		addSpellType(SpellType.PROJECTILE);
		setLore("Schießt ein Projektil, das einen getroffenen Gegner in der Zeit festhält. Dieser kann während dieser Zeit weder mit Zaubern noch mit seiner Waffe angreifen, erhält aber selbst auch keinen Rückstoß oder Schaden. Nach kurzer Zeit wird der Gegner befreit und erhält den gesamten Rückstoß, den er während der Versteinerung abbekommen hat, auf einmal.");
	}
	HashMap<ArmorStand,Vector> aList = new HashMap<ArmorStand,Vector>();
	@Override
	public void setUp() {

		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSound(Sound.ENTITY_EVOKER_CAST_SPELL,caster.getLocation(),1,1);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		loc.setDirection(caster.getLocation().getDirection());
		loc.add(loc.getDirection());
	}
	
	int r = 0;
	@Override
	public void display() {
		// TODO Auto-generated method stub
		//ParUtils.createParticle(Particles.END_ROD, loc, 0, 0, 0, 0, 1);
		Location l1 = ParUtils.stepCalcCircle(loc.clone(), 1, new Vector(0,1,0),0, step*6);
		ParUtils.dropItemEffectVector(l1, Material.CLOCK, 1, 0, 1, new Vector(0,0,0));
		Location l2 = ParUtils.stepCalcCircle(loc.clone(), 1, new Vector(0,1,0),0, 22+step*6);
		ParUtils.dropItemEffectVector(l2, Material.CLOCK, 1, 0, 1, new Vector(0,0,0));
		ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, l1, 0, 0, 0, 1, 0.5F, loc.toVector().subtract(l1.toVector()));
		ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, l2, 0, 0, 0, 1, 0.5F, loc.toVector().subtract(l2.toVector()));
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
		new VelocityTimeStop(p, caster, name);
		dead = true;
	}

	@Override
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		new VelocityTimeStop(ent, caster, name);
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
		
	}

}
