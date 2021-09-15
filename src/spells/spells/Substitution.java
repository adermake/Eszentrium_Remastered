package spells.spells;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import net.minecraft.server.v1_16_R3.Particles;
import net.minecraft.server.v1_16_R3.ItemStack.HideFlags;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Tnt;

public class Substitution extends Spell {

	boolean triggered = false;

	public Substitution() {
		steprange = 20 * 5;
		speed = 1;
		cooldown = 20 * 22;
		name = "§3Substitution";
		hitSpell = false;
		hitPlayer = false;
		hitEntity = false;
		addSpellType(SpellType.MOBILITY);
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.SELFCAST);
		setLore("§7Nach Aktivierung weicht der Spieler für kurze Zeit dem nächsten Angriff aus. Sollte das passieren, wird er kurz unsichtbar und bewegt sich schneller.");
		setBetterLore("§7Nach Aktivierung weicht der Spieler für kurze Zeit dem nächsten Angriff aus. Sollte das passieren, wird er kurz unsichtbar, bewegt sich schneller und hinterlässt eine Bombe, die nach kurzer Zeit explodiert und Gegnern Schaden zufügt.");
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
	
		Spell.hitSpellEvent.add(this);
		Spell.takeDamageEvent.add(this);
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub

	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		playSingleSound(Sound.BLOCK_BAMBOO_BREAK, caster, 5, 1);
		//ParUtils.createParticle(Particles.COMPOSTER, caster.getEyeLocation(), 1, 1, 1, 5, 0);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (triggered) {
			if (step > 20 && step < 40) {
				if (unHittable.contains(caster))
				unHittable.remove(caster);
			}
			if (step % 10 == 0)
				playSingleSound(Sound.BLOCK_NETHER_BRICKS_PLACE, caster, 1F, 1);
		}
		else {
			if (step % 10 == 0)
				playSingleSound(Sound.BLOCK_NETHER_BRICKS_PLACE, caster, 1F, 2);
		}
		loc = caster.getLocation();
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
		Spell.hitSpellEvent.remove(this);
		Spell.takeDamageEvent.remove(this);
		
		if (triggered) {
			
			PlayerUtils.showPlayer(caster);
			playSound(Sound.BLOCK_WOOD_FALL, caster.getLocation(), 5, 1);
			playSound(Sound.BLOCK_BAMBOO_STEP, caster.getLocation(), 5, 2);
			playSound(Sound.ENTITY_PUFFER_FISH_BLOW_UP, caster.getLocation(), 5, 1);
			
			ParUtils.createParticle(Particles.EXPLOSION, caster.getLocation(), 0, 0, 0, 1, 0);
			ParUtils.createFlyingParticle(Particles.CLOUD, loc, 0.5F, 0.5F, 0.5F, 10, 1, new Vector(0,1,0));
			ParUtils.createParticle(Particles.HAPPY_VILLAGER, caster.getLocation(), 1, 1, 1, 10, 0);
		}
		
		
	}

	@Override
	public boolean callEvent(String s) {

		if (!triggered) {

			if (s.equals("spellHitPlayerEvent") || s.equals("takeDamageEvent")) {
				
				if (refined) {
					new Tnt(caster,name);
				}
				else {
					spawnFallingBlock(caster.getEyeLocation(), Material.JUNGLE_LOG);
				}
				
				playSound(Sound.BLOCK_WOOD_FALL, caster.getLocation(), 5, 1);
				playSound(Sound.BLOCK_BAMBOO_STEP, caster.getLocation(), 5, 1);
				playSound(Sound.BLOCK_NOTE_BLOCK_COW_BELL, caster.getLocation(), 5, 0.5F);
				playSound(Sound.ENTITY_PUFFER_FISH_BLOW_OUT, caster.getLocation(), 5, 1);
				ParUtils.createParticle(Particles.EXPLOSION, caster.getLocation(), 0, 0, 0, 1, 0);
				ParUtils.createParticle(Particles.HAPPY_VILLAGER, caster.getLocation(), 1, 1, 1, 10, 0);
				PlayerUtils.hidePlayer(caster);
				triggered = true;
				step = 0;
				steprange = 20 * 3;
				caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,steprange,3));
				caster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,steprange,3));
				if (!unHittable.contains(originalCaster)) {
					unHittable.add(originalCaster);
				}
				
				return true;
			}

		}

		return false;
	}

}
