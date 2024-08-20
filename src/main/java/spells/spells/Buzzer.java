package spells.spells;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;

public class Buzzer extends Spell {

	
	public Buzzer() {
		cooldown = 20 * 3;
		steprange = 5;
		
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		caster.setVelocity(new Vector(0,1.5,0));
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
		ParUtils.createParticle(Particle.FIREWORK, caster.getLocation(), 0,-1, 0, 0, 1);
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
		
		Firework firework = (Firework) caster.getWorld().spawnEntity(caster.getLocation().add(0,5,0), EntityType.FIREWORK_ROCKET);
		
		
		
		

	       

        FireworkMeta fd = (FireworkMeta) firework.getFireworkMeta();

        fd.addEffect(FireworkEffect.builder()

                .flicker(true)

                .trail(true)

                .with(Type.BALL)

                .with(Type.BALL_LARGE)

                

                .withColor(Color.fromRGB(randInt(1, 255), randInt(1, 255),randInt(1, 255)))
                .withColor(Color.fromRGB(randInt(1, 255), randInt(1, 255),randInt(1, 255)))
               

                

                

                .build());

        firework.setFireworkMeta(fd);
	firework.detonate();
	}

}
