package spells.spells;

import esze.utils.ParUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Beben extends Spell {

    public Beben() {

        name = "§eBeben";
        steprange = 22;
        cooldown = 20 * 40;
        hitEntity = true;
        hitPlayer = true;
        hitSpell = true;
        hitBlock = false;
        hitboxSize = 3;
        casttime = 20;
        speed = 4;

        addSpellType(SpellType.PROJECTILE);
        addSpellType(SpellType.DAMAGE);
        addSpellType(SpellType.KNOCKBACK);
        setLore("§7Schießt eine Schockwelle geradeaus in#§7Blickrichtung durch den Boden.#§7Gegner die damit in Kontakt kommen,#§7werden weggeschleudert und erhalten Schaden.#§7Wird der Zauber in der Luft ausgeführt,#§7wird der Spieler nach unten geschmettert#§7und die Schwockwelle wird bei der#§7Landung freigesetzt. Je tiefer der Spieler nach#§7Aktivierung fällt, desto stärker#§7wird die Schockwelle.");
        setBetterLore("§7Schießt eine Schockwelle geradeaus in#§7Blickrichtung durch den Boden.#§7Gegner die damit in Kontakt kommen,#§7werden weggeschleudert und erhalten Schaden.#§7Wird der Zauber in der Luft ausgeführt,#§7wird der Spieler nach unten geschmettert#§7und die Schwockwelle wird bei der#§7Landung freigesetzt. Je tiefer der Spieler nach#§7Aktivierung fällt, desto stärker#§7wird die Schockwelle.");
    }

    @Override
    public void onDeath() {
        // TODO Auto-generated method stub

    }

    double height = 0;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        height = caster.getLocation().getY();
        caster.setVelocity(caster.getVelocity().add(new Vector(0, -6, 0)));
        ParUtils.createFlyingParticle(Particle.CLOUD, caster.getLocation(), 0, 2, 0, 10, 1, new Vector(0, -1, 0));
        ParUtils.parKreisDot(Particle.CLOUD, caster.getLocation(), 2, 0, 2, new Vector(0, 1, 0));
        playSound(Sound.ENTITY_MOOSHROOM_CONVERT, caster.getLocation(), 15, 2);
    }

    @Override
    public void cast() {
        if (caster.isOnGround() == true) {
            height = height - caster.getLocation().getY();
            if (height < 2)
                height = 2;


            steprange += height;
            cast = casttime;
            if (refined)
                steprange = steprange * 2;

        } else {
            cast = 0;
        }


    }

    @Override
    public void launch() {
        // TODO Auto-generated method stub
        loc = caster.getLocation();
        direction = caster.getLocation().getDirection();
    }

    Vector direction;

    @Override
    public void move() {

        double x = direction.getX();
        double y = 0;
        double z = direction.getZ();
        loc.add(x, y, z);
        int minus = 0;
        while (loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
            loc.add(0, -1, 0);
            minus++;
            if (minus >= 256) {
                break;
            }
        }
        int plus = 0;
        while (loc.getBlock().getType() != Material.AIR) {
            loc.add(0, 1, 0);
            plus++;
            if (plus >= 14) {
                break;
            }
        }
        playSound(Sound.ENTITY_GENERIC_EXPLODE, loc, 1, (float) 0.1);
        @SuppressWarnings("deprecation")
        MaterialData md = new MaterialData(loc.getBlock().getRelative(BlockFace.DOWN).getType(), loc.getBlock().getRelative(BlockFace.DOWN).getData());
        if (loc.getBlock().getType() == Material.AIR) {
            FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
            f.setVelocity(f.getVelocity().setY(0.7));


        }
        if (loc.add(0, 0, 1).getBlock().getType() == Material.AIR) {
            FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
            f.setVelocity(f.getVelocity().setY(0.7));
            f.setDropItem(false);

        }
        if (loc.add(0, 0, -1).getBlock().getType() == Material.AIR) {
            FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
            f.setVelocity(f.getVelocity().setY(0.7));
            f.setDropItem(false);

        }
        if (loc.add(-1, 0, 0).getBlock().getType() == Material.AIR) {
            FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
            f.setVelocity(f.getVelocity().setY(0.7));
            f.setDropItem(false);

        }
        if (loc.add(1, 0, 0).getBlock().getType() == Material.AIR) {
            FallingBlock f = (FallingBlock) loc.getWorld().spawnFallingBlock(loc, md);
            f.setVelocity(f.getVelocity().setY(0.7));
            f.setDropItem(false);

        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPlayerHit(Player p) {

        double h = 1;
        if (refined) {
            h += height / 3;
        } else {
            h += height / 4;
        }
        if (refined) {
            if (h > 15)
                h = 15;
        } else {
            if (h > 10)
                h = 10;
        }


        Vector dir = direction.clone().normalize();
        dir = dir.setY(0);
        dir = dir.normalize();

        int hi = (int) h;
        damage(p, hi + 2, caster);
        if (refined) {
            p.setVelocity(dir.normalize().multiply(7).add(new Vector(0, 2F, 0)));
        } else {
            p.setVelocity(dir.normalize().multiply(6).add(new Vector(0, 1.5F, 0)));
        }

        //p.setVelocity(p.getVelocity().setY(1.0D));

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

        double h = 1;
        if (refined) {
            h += height / 2;
        } else {
            h += height / 4;
        }
        if (refined) {
            if (h > 15)
                h = 15;
        } else {
            if (h > 10)
                h = 10;
        }
        int hi = (int) h;
        damage(ent, hi + 2, caster);
        Vector dir = direction.clone();
        dir = dir.setY(0);
        dir = dir.normalize();

        if (refined) {
            ent.setVelocity(dir.normalize().multiply(7).add(new Vector(0, 2F, 0)));
        } else {
            ent.setVelocity(dir.normalize().multiply(6).add(new Vector(0, 1.5F, 0)));
        }

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
