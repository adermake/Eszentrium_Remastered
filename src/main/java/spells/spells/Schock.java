package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Color;
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
import spells.stagespells.SchockLaser;

public class Schock extends Spell {


    public Schock() {
        name = "§cSchock";
        //casttime =  3;
        aim = null;
        steprange = 3;
        spellDescription = new SpellDescription(
                "Schießt Blitze in Blickrichtung, die an getroffenen Gegnern Schaden verursachen. Der Schaden der Blitze steigt, wenn der Zauber aus großer Höhe ausgeführt wird.",
                "Schießt Blitze aus großer Höhe auf den anvisierten Block und verursacht Schaden an getroffenen Gegnern.",
                null,
                null,
                "Halten, um Blitze auszuweiten.",
                "Halten, um Blitze auszuweiten.",
                20*40
        );
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.PROJECTILE);

        casttime = 20 * 6;

    }

    double height = 0;
    double castheight = 0;
    Location aim;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
		/*
		if (refined) {
			
			
			aim = block(caster);
			if (aim == null) {
				refund = true;
				dead = true;
			}
		}
		*/

        //ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 0.1f, randVector());
        //ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 0.1f, randVector());
        castheight = caster.getLocation().getY();
        playGlobalSound(Sound.ENTITY_BLAZE_DEATH, 5, 0.8F);

    }

    @Override
    public void cast() {

        float c = cast;
        float ct = casttime;
        if (cast < 5)
            playGlobalSound(Sound.ENTITY_BLAZE_DEATH, 5, 0.8F);
        playSound(Sound.BLOCK_SHROOMLIGHT_HIT, caster.getLocation(), 4, 2 * (c / ct));
        // TODO Auto-generated method stub
        Location l1 = caster.getLocation();
        l1.setY(castheight);
        ParUtils.parLineRedstone(caster.getLocation(), l1, Color.GRAY, 1F, 0.5D);
        //ParUtils.createParticle(Particle.END_ROD, caster.getLocation(), 0, -1, 0, 0,1);
        ParUtils.createFlyingParticle(Particle.END_ROD, caster.getLocation(), 1, 1, 1, 1, 1, new Vector(0, -1, 0));
        if (swap()) {
            cast = casttime;
        }

    }

    @Override
    public void launch() {

        // TODO Auto-generated method stub

        playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, caster.getLocation(), 4, 0.2F);
        ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
        ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
        ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
        ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
        ParUtils.parKreisDot(Particle.END_ROD, caster.getLocation(), 3, 1, 1, randVector());
    }

    @Override
    public void move() {

        height = caster.getLocation().getY() - castheight;
        if (height < 0)
            height = 0;
        if (refined) {
            if (aim == null)
                return;
            new SchockLaser(caster.getLocation().add(0, 100, 0), aim.clone(), caster, name, refined, height);
            new SchockLaser(caster.getLocation().add(0, 100, 0), aim.clone(), caster, name, refined, height);
            new SchockLaser(caster.getLocation().add(0, 100, 0), aim.clone(), caster, name, refined, height);

        } else {
            new SchockLaser(caster, name, refined, height);
            new SchockLaser(caster, name, refined, height);
            new SchockLaser(caster, name, refined, height);
        }

        playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, caster.getLocation(), 8F, 2f);
        // TODO Auto-generated method stub

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

    }


}
