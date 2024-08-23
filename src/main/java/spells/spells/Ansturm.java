package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;

public class Ansturm extends Spell {

    IronGolem golem;


    public Ansturm() {
        name = "§6Ansturm";
        hitEntity = true;
        steprange = 60;
        speed = 1;
        hitboxSize = 2;
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                20*24
        );
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub
        golem.remove();
    }

    @Override
    public void setUp() {
        golem = (IronGolem) spawnEntity(EntityType.IRON_GOLEM);
        noTargetEntitys.add(golem);
        Location l = loc.clone();
        l.setPitch(0);
        moveDir = l.getDirection();
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }

    Vector moveDir;

    @Override
    public void move() {
		/*
		if (!caster.isSneaking()) {
			loc.setDirection(loc.getDirection().add(new Vector(0,0.1,0)));
		}
		*/
        if (caster.isSneaking()) {
            // REFINED
            if (refined) {
                canHitSelf = true;

            }

            // REFINED
            for (BlockFace bf : BlockFace.values()) {
                if (bf == BlockFace.DOWN)
                    continue;
                if (golem.getLocation().getBlock().getRelative(bf).getType() != Material.AIR) {
                    golem.setVelocity(moveDir.clone().multiply(-1).setY(0.5D));
                    break;
                } else {
                    golem.setVelocity(moveDir.clone().multiply(-1).setY(-1D));
                }
            }
        } else {
            canHitSelf = false;
            for (BlockFace bf : BlockFace.values()) {
                if (bf == BlockFace.DOWN)
                    continue;
                if (golem.getLocation().getBlock().getRelative(bf).getType() != Material.AIR) {
                    golem.setVelocity(moveDir.clone().multiply(1).setY(0.5D));

                    break;
                } else {
                    golem.setVelocity(moveDir.clone().multiply(1).setY(-1D));
                }

            }
        }
        Vector dir = loc.getDirection();
        loc = golem.getLocation();
        loc.setDirection(dir);

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {
        double velAdder = 0;
        if (refined)
            velAdder = 3;
        if (caster != p)
            damage(p, 5, caster);
        if (caster.isSneaking()) {

            p.setVelocity(loc.getDirection().multiply(3 + velAdder));
        } else {
            p.setVelocity(loc.getDirection().multiply(-3 - velAdder));
        }

        playSound(Sound.ENTITY_IRON_GOLEM_ATTACK, loc, 1, 1);
        p.setVelocity(p.getVelocity().setY(2.0D));
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        double velAdder = 0;
        if (refined)
            velAdder = 3;
        damage(ent, 5, caster);
        if (caster.isSneaking()) {
            ent.setVelocity(loc.getDirection().multiply(3 + velAdder));
        } else {
            ent.setVelocity(loc.getDirection().multiply(-3 - velAdder));
        }
        playSound(Sound.ENTITY_IRON_GOLEM_ATTACK, loc, 1, 1);
        ent.setVelocity(ent.getVelocity().setY(2.0D));
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {
        // TODO Auto-generated method stub

    }


}
