package spells.spells;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

public class SpikeArray extends Spell {


    public SpikeArray() {
        name = "§eSpikeArray";
        speed = 1;
        steprange = 50;
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20
        );
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

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
        while (!loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            loc.add(0, -1, 0);
        }
        while (loc.getBlock().getType().isSolid()) {
            loc.add(0, 1, 0);
        }

        loc.add(loc.getDirection().multiply(1.5F));
        spawnEntity(EntityType.EVOKER_FANGS, loc.clone().add(0, -1, 0));
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
