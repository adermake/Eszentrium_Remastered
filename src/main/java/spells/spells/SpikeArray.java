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

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {
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
