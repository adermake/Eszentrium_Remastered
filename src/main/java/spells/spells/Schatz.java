package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import esze.utils.ParUtils;

import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

public class Schatz extends Spell {

    public Schatz() {
        name = "§3Schatz";
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

    public void setUp() {
        // TODO Auto-generated method stub
        Bukkit.broadcastMessage("XX");
        ParUtils.debug(caster.getLocation().add(caster.getLocation().getDirection().multiply(5)));
        caster.getWorld().spawnFallingBlock(caster.getLocation().add(caster.getLocation().getDirection().multiply(5)), Material.TRAPPED_CHEST, (byte) 1);
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
