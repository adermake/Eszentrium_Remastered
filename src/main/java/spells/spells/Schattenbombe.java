package spells.spells;

import esze.utils.PlayerUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import spells.spellcore.Spell;

public class Schattenbombe extends Spell {


    public Schattenbombe() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

        PlayerUtils.hidePlayer(caster);
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    int damage = 0;

    @Override
    public void move() {
        // TODO Auto-generated method stub


        damage++;
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
        PlayerUtils.showPlayer(caster);
    }

}
