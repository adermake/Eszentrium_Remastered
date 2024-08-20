package spells.spells;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Sythe;

public class Todessaege extends Spell {

    public HashMap<Entity, Integer> hitCount = new HashMap<Entity, Integer>();

    public Todessaege() {
        name = "�eTodess�ge";
        cooldown = 20 * 40;
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.MULTIHIT);
        addSpellType(SpellType.SUPPORT);
        addSpellType(SpellType.PROJECTILE);
        setLore("�7Wirft Kreiss�gen in Blickrichtung, die auf kurzer Distanz nach unten fallen und dort liegen bleiben. Getroffene Gegner werden zur�ckgeworfen. Nach kurzer Zeit kehren die S�gen zum Anwender zur�ck, Schaden getroffenen Gegnern und heilen den Anwender f�r jeden Treffer. �eShift: �7Zieht die S�gen sofort zur�ck.");
        setBetterLore("�7Wirft Kreiss�gen in Blickrichtung, die auf kurzer Distanz nach unten fallen und dort liegen bleiben. Getroffene Gegner werden zur�ckgeworfen. Nach kurzer Zeit kehren die S�gen zum Anwender zur�ck, Schaden getroffenen Gegnern und heilen den Anwender f�r jeden Treffer. �eShift: �7Zieht die S�gen sofort zur�ck.");
        autocancel = true;
    }


    @Override
    public void setUp() {
        // TODO Auto-generated method stub


        Location l = caster.getLocation();

        float degStep = 8;
        int count = 11;

        if (refined) {
            degStep = 10;
            count = 35;
        }
        l.setYaw(l.getYaw() - (float) ((count / 2) * degStep));
        for (float i = 0; i < count; i++) {


            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 1 * (float) ((Math.PI * 2) / 3), this, refined);
            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 2 * (float) ((Math.PI * 2) / 3), this, refined);
            new Sythe(name, caster, caster.getEyeLocation(), l.getDirection(), 3 * (float) ((Math.PI * 2) / 3), this, refined);
            l.setYaw(l.getYaw() + degStep);
        }
        playSound(Sound.ITEM_TRIDENT_THROW, loc, 1, 0.2F);


    }

    public void addHitCount(Entity ent) {
        if (hitCount.containsKey(ent)) {
            hitCount.put(ent, hitCount.get(ent) + 1);

        } else {
            hitCount.put(ent, 1);
        }
    }

    public int getHitCount(Entity ent) {
        if (hitCount.containsKey(ent)) {
            return hitCount.get(ent);
        } else {
            return 0;
        }
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
