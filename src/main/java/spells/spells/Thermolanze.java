package spells.spells;

import esze.utils.ParUtils;
import esze.utils.SoundUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;
import spells.stagespells.ThermolanzeLaser;

public class Thermolanze extends Spell {

    Location dirLoc;

    public Thermolanze() {
        name = "§eThermolanze";
        steprange = 30;

        speed = 2;
        casttime = 10;
        hitSpell = false;

        spellDescription = new SpellDescription(
                "Der Spieler springt in die Luft und schießt einen Feuerstrahl in Blickrichtung. Getroffene Gegner werden weggeschleudert.",
                "Der Spieler springt in die Luft und schießt einen Feuerstrahl in Blickrichtung. Getroffene Gegner werden weggeschleudert.",
                null,
                null,
                null,
                null,
                20*32
        );
        
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.MULTIHIT);
    }

    @Override
    public void setUp() {
        SoundUtils.playSound(Sound.ENTITY_IRON_GOLEM_DEATH, caster.getLocation(), 1F, 50);
        caster.setVelocity(caster.getVelocity().setY(2));
        ParUtils.parKreisDir(Particle.LARGE_SMOKE, loc, 4, 0, 1, new Vector(0, 1, 0), new Vector(0, 1, 0));
        if (refined) {
            steprange = 70;
        }
    }

    @Override
    public void cast() {
        ParUtils.dashParticleTo(Particle.SMOKE, caster, caster.getLocation().add(randVector().multiply(15)));
        caster.setVelocity(caster.getVelocity().add(new Vector(0, 0.1F, 0)));
    }


    @Override
    public void launch() {
        dirLoc = caster.getLocation();
        dirLoc.setPitch(80);
    }


    @Override
    public void move() {
        if (caster.getGameMode() == GameMode.ADVENTURE)
            dead = true;

        dirLoc.setPitch(caster.getLocation().getPitch() + 1);
        dirLoc.setYaw(caster.getLocation().getYaw());
        new ThermolanzeLaser(caster, dirLoc.getDirection(), false);
        playSound(Sound.ENTITY_HUSK_DEATH, loc, 10F, 1F);

        if (refined) {
            if (caster.getVelocity().getY() < 0)
                caster.setVelocity(caster.getVelocity().setY(caster.getVelocity().getY() / 2));
        }
    }

    @Override
    public void display() {

    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {

    }

}
