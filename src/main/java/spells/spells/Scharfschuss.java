package spells.spells;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;
import org.bukkit.Particle;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Scharfschuss extends Spell {

    public Scharfschuss() {
        name = "§cScharfschuss";
        hitSpell = true;
        steprange = 300;
        speed = 100;
        casttime = 40;
        cooldown = 20 * 55;
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);

        setLore("§7Schießt nach kurzer Verzögerung#§7ein Projektil in Blickrichtung, das#§7getroffene Gegner sofort tötet.");
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

    }

    int t = 0;
    int stage = 0;

    @Override
    public void cast() {
        t++;
        stage++;
        if (t < 20) {
            ParUtils.dashParticleTo(Particle.FLAME, caster, loc);
            ParUtils.dashParticleTo(Particle.FLAME, caster, loc);
            ParUtils.dashParticleTo(Particle.FLAME, caster, loc);
        }

        if (t < 40) {
            if (stage > 2) {
                playGlobalSound(Sound.ENTITY_ELDER_GUARDIAN_DEATH_LAND, 1, 1);
                stage = 0;
            }

        }


    }

    @Override
    public void move() {
        loc.add(loc.getDirection());

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.RED, 1);
        ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.FUCHSIA, 1);
        ParUtils.createParticle(Particle.CRIT, loc, 0, 0, 0, 1, 0);
        ParUtils.createParticle(Particle.SMOKE, loc, 0, 0, 0, 1, 0);
    }

    @Override
    public void onPlayerHit(Player p) {
        damage(p, 20, caster);
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        damage(ent, 20, caster);
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        playSound(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, loc, 10, 1);
        ParUtils.createParticle(Particle.EXPLOSION_EMITTER, loc, 0, 0, 0, 1, 0);
        dead = true;
    }

    @Override
    public void launch() {
        loc = caster.getEyeLocation();
        playSound(Sound.ENTITY_ZOMBIE_INFECT, loc, 10, 1);

    }


}
