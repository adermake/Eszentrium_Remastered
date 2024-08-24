package spells.spells;

import esze.utils.ParUtils;
import esze.utils.PlayerUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class Requiemspfeil extends Spell {

    Location toLoc;
    Vector dir;
    Arrow a;

    public Requiemspfeil() {

        name = "§eRequiemspfeil";
        steprange = 100;
        hitboxSize = 1.5;
        speed = 1;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Verwandelt den Spieler für kurze Zeit in einen Pfeil, der sich in Blickrichtung fortbewegt. Wird ein Gegner von diesem Pfeil getroffen, erleidet er Schaden und der Zauber wird beendet.",
                "Verwandelt den Spieler für kurze Zeit in einen Pfeil, der sich in Blickrichtung fortbewegt. Wird ein Gegner von diesem Pfeil getroffen, erleidet er Schaden und der Zauber wird beendet.",
                null,
                null,
                "Pfeil beschleunigen und schaden erhöhen.",
                "Pfeil beschleunigen und schaden erhöhen.",
                20*40
        );

        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.AURA);
        addSpellType(SpellType.PROJECTILE);

    }

    Location ori;
    Location point;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        a = (Arrow) spawnEntity(EntityType.ARROW, caster.getEyeLocation().add(caster.getLocation().getDirection()));

        dir = caster.getLocation().getDirection();
        ori = caster.getLocation();
        // caster.setGameMode(GameMode.SPECTATOR);
        caster.teleport(caster.getLocation().add(0, 1, 0));
        setGliding(caster, true);
        PlayerUtils.hidePlayer(caster);
        bindEntity(a);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20000, 1, true));

    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    double dist = 2;
    int speedX = 0;

    @Override
    public void move() {
        if (swap()) {
            if (dist != 6) {
                dist = 6;
            } else {
                dist = 3;
            }

        }
        clearswap();
        if (a.isOnGround()) {
            dead = true;
        }
        float f = ((float) speedX) / 50;


        caster.setNoDamageTicks(20);
        // TODO Auto-generated method stub
        loc = a.getLocation();
        // a.teleport(caster.getLocation().add(caster.getLocation().getDirection().multiply(4)));
        speedX++;
        if (caster.isSneaking()) {
            playGlobalSound(Sound.BLOCK_NOTE_BLOCK_FLUTE, 0.3F, f);
            a.setVelocity(caster.getLocation().getDirection().multiply(2.5));
            //ParUtils.createFlyingParticle(Particle.CLOUD, a.getLocation(), 2, 2,2,5, -3, a.getVelocity());
            // playSound(Sound.BLOCK_LAVA_EXTINGUISH,a.getLocation(),0.1F,0.4F);

        } else {
            a.setVelocity(caster.getLocation().getDirection().multiply(1));
            playGlobalSound(Sound.BLOCK_NOTE_BLOCK_FLUTE, 0.3F, f);
        }

        if (speedX <= 0)
            speedX = 0;
        if (speedX > 50)
            speedX = 50;
        doPull(caster, a.getLocation(), a.getLocation().distance(caster.getLocation()) / dist);
        // Location aim = loc(caster,step);
        // doPull(a,aim,a.getLocation().distance(aim)/5);
        loc = a.getLocation();
        if (silenced.containsKey(caster)) {
            dead = true;
        }
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        int color = ((int) speedX * 2);
        if (color > 255)
            color = 255;
        ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.fromBGR(color, color, 0), 1.5F);
    }

    @Override
    public void onPlayerHit(Player p) {
        playSound(Sound.ENTITY_ARROW_HIT_PLAYER, ori, 5, 1);
        playSound(Sound.ENTITY_ARROW_HIT_PLAYER, caster.getLocation(), 5, 1);
        // TODO Auto-generated method stub


        if (refined) {
            damage(p, 2 + speedX / 5, caster);
            reduceCooldown(20 * 25);
        } else {
            damage(p, speedX / 5, caster);
            reduceCooldown(20 * 10);
        }

        dead = true;

    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub
        // ParUtils.createParticle(Particle.EXPLOSION_HUGE, toLoc, 0,0, 0, 1, 1);
        // REMOVED CAUSED ERRORS
        playSound(Sound.ENTITY_ARROW_HIT_PLAYER, ori, 5, 1);
        playSound(Sound.ENTITY_ARROW_HIT_PLAYER, caster.getLocation(), 5, 1);
        if (refined) {
            damage(ent, 2 + speedX / 5, caster);
            reduceCooldown(20 * 20);
        } else {
            damage(ent, speedX / 5, caster);
            reduceCooldown(20 * 10);
        }


        dead = true;

    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub
        /*
         * if (spell.isSpellType(SpellType.PROJECTILE) ||
         * spell.isSpellType(SpellType.AURA)) {
         *
         *
         * ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, 5);
         * setGliding(originalCaster, false); originalCaster.setVelocity(new
         * Vector(0,0,0)); once = true; PlayerUtils.showPlayer(originalCaster);
         * a.remove();
         *
         * originalCaster.removePotionEffect(PotionEffectType.INVISIBILITY);
         *
         * originalCaster.teleport(ori);
         * caster.setVelocity(caster.getVelocity().multiply(0)); dead = true; }
         */

        if (spell.getName().contains("Antlitz der Göttin")) {
            originalCaster.setNoDamageTicks(1);
            //ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, 5);
            setGliding(originalCaster, false);
            originalCaster.setVelocity(new Vector(0, 0, 0));
            //once = true;
            PlayerUtils.showPlayer(originalCaster);

            originalCaster.removePotionEffect(PotionEffectType.INVISIBILITY);
            // TODO Auto-generated method stub
            originalCaster.teleport(ori);
            ori = spell.caster.getLocation();
            setGliding(spell.caster, true);
            PlayerUtils.hidePlayer(spell.caster);

            spell.caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20000, 1, true));
        }

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub
        dead = true;
    }

    boolean once = false;

    @Override
    public void onDeath() {
        if (!once) {
            caster.setNoDamageTicks(1);
            ParUtils.createRedstoneParticle(loc, 0, 0, 0, 1, Color.WHITE, 5);
            setGliding(caster, false);
            caster.setVelocity(new Vector(0, 0, 0));
            once = true;
            PlayerUtils.showPlayer(caster);
            a.remove();
            caster.removePotionEffect(PotionEffectType.INVISIBILITY);
            // TODO Auto-generated method stub
            caster.teleport(ori);

        }

    }

}
