package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

import java.util.HashMap;

public class PotionSeller extends Spell {


    HashMap<Integer, ThrownPotion> potions = new HashMap<Integer, ThrownPotion>();

    public PotionSeller() {

    }

    @Override
    public void setUp() {
        potions.put(0, (ThrownPotion) spawnEntity(EntityType.POTION));
        potions.put(1, (ThrownPotion) spawnEntity(EntityType.POTION));
        potions.put(2, (ThrownPotion) spawnEntity(EntityType.POTION));
    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {

    }

    @Override
    public void display() {
        for (float i = 0; i < 3; i++) {
            Location holoLoc = caster.getLocation();
            holoLoc.setDirection(loc.getDirection());
            Location l1 = ParUtils.stepCalcCircle(holoLoc, 3, new Vector(0, 1, 0), 1, 7 + i * 5);
            if (potions.get((int) i).isDead()) {
                potions.put((int) i, (ThrownPotion) spawnEntity(EntityType.POTION, l1));
            }
            doPin(potions.get((int) i), l1);
        }
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
