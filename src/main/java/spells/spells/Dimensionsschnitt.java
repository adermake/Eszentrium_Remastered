package spells.spells;

import esze.utils.ParUtils;
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
import spells.stagespells.DimensionCutSeg;

public class Dimensionsschnitt extends Spell {


    public Dimensionsschnitt() {
        // TODO Auto-generated constructor stub
        name = "§bDimensionsschnitt";
        steprange = 100;
        speed = 10;
        spellDescription = new SpellDescription(
                "Der Spieler springt in Blickrichtung und hinterlässt eine raumverzerrende Spur, die beim Erscheinen getroffenen Gegnern Schaden zufügt. Gegner, die mit der Spur in Kontakt kommen, werden auf die andere Seite der Spur teleportiert.",
                "Der Spieler springt in Blickrichtung und hinterlässt eine raumverzerrende Spur, die beim Erscheinen getroffenen Gegnern Schaden zufügt. Gegner, die mit der Spur in Kontakt kommen, werden auf die andere Seite der Spur teleportiert.",
                null,
                null,
                "Bricht den Sprung ab.",
                "Bricht den Sprung ab.",
                20*45
        );
        addSpellType(SpellType.SELFCAST);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        if (refined) {
            steprange = 150;
        }
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        playSound(Sound.BLOCK_END_PORTAL_SPAWN, loc, 1, 0.8F);
        Location dloc = loc.clone();
        dloc.setYaw(dloc.getYaw() + 90);
        dloc.setPitch(0);
        Vector d = dloc.getDirection();
        for (double i = 0; i < 4 * 44; i++) {

            Location l = ParUtils.stepCalcCircle(loc.clone(), 3, d, -1, i / 8 - 11);
            Vector v = l.toVector().subtract(loc.toVector());

            //ParUtils.debug(loc.clone());
            ParUtils.createFlyingParticle(Particle.END_ROD, l, 0, 0, 0, 1, 2, v);

        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        loc.add(loc.getDirection().multiply(0.5));
        doPin(caster, loc.clone());
		/*
		Location bot = getFloor(loc.clone(),30).add(0,1,0);
		Location top = getCieling(loc.clone(),30).add(0,-1,0);
		
		if (loc.distance(top)> 1.1) {
			ParUtils.createParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 60, 0.2);
		}
		
		ParUtils.createParticle(Particle.BARRIER, top, 0, 0, 0, 1, 1);
		if (loc.distance(bot)> 1.1)
		ParUtils.createParticle(Particle.BARRIER, bot, 0, 0, 0, 1, 1);
		
		ParUtils.createParticle(Particle.SMOKE_LARGE, loc, 0, 0, 0, 60, 0.2);
		*/
        new DimensionCutSeg(caster, loc.clone(), name, refined, true, loc.getDirection());
        new DimensionCutSeg(caster, loc.clone(), name, refined, false, loc.getDirection());

        if (caster.isSneaking()) {
            caster.setVelocity(caster.getLocation().getDirection().multiply(-2));
            dead = true;
        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //ParUtils.createParticle(Particle.WATER_BUBBLE, loc, 0, 0, 0, 1, 0);
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

    }

}
