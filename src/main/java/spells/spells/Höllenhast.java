package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Höllenhast extends Spell {

    public Höllenhast() {
        steprange = 70;
        speed = 9;
        charges = 10;
        name = "§eHöllenhast";
        hitboxSize = 2;

        spellDescription = new SpellDescription(
                "Schießt einen Feuerball in Blickrichtung, der bei Gegner- oder Blockkontakt explodiert und an getroffenen Spielern Schaden anrichtet. Der Schuss hat einen starken Rückstoß.",
                "Schießt einen Feuerball in Blickrichtung, der bei Gegner- oder Blockkontakt explodiert und an getroffenen Spielern Schaden anrichtet. Der Schuss hat einen starken Rückstoß.",
                "Zauber nochmal ausführen (falls er getroffen hat; max. 10x möglich)",
                "Zauber nochmal ausführen (max. 10x möglich)",
                null,
                null,
                20*40
        );

        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.MOBILITY);
        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.SELFCAST);
    }

    int charges = 0;

    public Höllenhast(Player c, String name, int charge, boolean refined) {
        super();
        this.refined = refined;
        steprange = 70;
        speed = 9;
        this.name = name;
        this.caster = c;
        casttime = 100;
        castSpell(c, name);
        hitboxSize = 1;
        charges = charge;

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        playSound(Sound.ENTITY_BLAZE_AMBIENT, caster.getLocation(), 1, 1);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub
        if (cast >= casttime) {
            dead = true;
        }
        if (swap()) {
            cast = casttime;
        }
        ParUtils.createFlyingParticle(Particle.FLAME, caster.getEyeLocation(), 0.9, 0.9, 0.9, 1, -0.4F, caster.getLocation().getDirection().multiply(-2));

    }

    @Override
    public void launch() {
        if (dead) {
            return;
        }
        playSound(Sound.ENTITY_BLAZE_SHOOT, caster.getLocation(), 1, 1);
        caster.setVelocity(caster.getLocation().getDirection().multiply(-1.5));
        loc = caster.getEyeLocation();
        // TODO Auto-generated method stub

        ParUtils.parKreisDir(Particle.CAMPFIRE_COSY_SMOKE, caster.getLocation(), 3, 0, 1, caster.getLocation().getDirection(), caster.getLocation().getDirection());
        if (refined && charges > 0) {

            clearswap();
            new Höllenhast(caster, name, charges - 1, refined);
        }
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        loc.add(loc.getDirection().multiply(0.5F));
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        for (Item i : ParUtils.dropItemEffectRandomVector(loc, Material.GOLDEN_APPLE, 1, 2, 0)) {
            i.setFireTicks(2000);
        }
        if (step < 20)
            ParUtils.createFlyingParticle(Particle.FLAME, loc, 0.2F, 0.2F, 0.2F, 5, 1, loc.getDirection().multiply(2));
    }

    @Override
    public void onPlayerHit(Player p) {
        new spells.stagespells.Explosion(1, 2, 1, 1, caster, loc, name);

        // TODO Auto-generated method stub
        damage(p, 4, caster);
        clearswap();
        if (charges > 0 && !refined)
            new Höllenhast(caster, name, charges - 1, refined);
        dead = true;
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

        damage(ent, 4, caster);
        new spells.stagespells.Explosion(2, 2, 1, 1, caster, loc.add(loc.getDirection().multiply(-1)), name);
        clearswap();
        if (charges > 0 && !refined)
            new Höllenhast(caster, name, charges - 1, refined);
        dead = true;
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        new spells.stagespells.Explosion(2, 2, 1, 1, caster, loc.add(loc.getDirection().multiply(-1)), name);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (checkHit(p, loc, caster, 2)) {
                if (charges > 0 && !refined)
                    new Höllenhast(caster, name, charges - 1, refined);
            }
        }
        dead = true;
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

}
