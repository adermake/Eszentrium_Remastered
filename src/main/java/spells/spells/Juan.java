package spells.spells;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;

public class Juan extends Spell {
    Vector offset;

    public Juan(Player p, String name, Vector offset) {

        this.offset = offset;
        cooldown = 20 * 10;
        name = "§bKeksspell";
        speed = 1;
        hitboxSize = 2;
        hitPlayer = true;
        hitSpell = true;
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
        steprange = 100;
        castSpell(p, name);
    }

    Skeleton dave;
    ZombieHorse juan;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        dave = (Skeleton) spawnEntity(EntityType.SKELETON);
        juan = (ZombieHorse) spawnEntity(EntityType.ZOMBIE_HORSE, loc.add(offset));
        juan.setTamed(true);
        addNoTarget(juan);
        unHittable.add(juan);
        addNoTarget(dave);
        unHittable.add(dave);
        juan.setGravity(false);
        juan.addPassenger(dave);
        dave.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON));
        Vector d = loc.getDirection();
        d.setY(0);
        loc.setDirection(d);


    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        juan.setVelocity(loc.getDirection().multiply(0.7));
        Vector a = loc.getDirection();
        loc = juan.getLocation();
        loc.setDirection(a);
        if (step % 25 == 0) {
            new Pfeilsucher(caster, name, dave.getLocation().add(new Vector(0, 2, 0)));
            playSound(Sound.ENTITY_HORSE_GALLOP, loc, 0.1f, 1f);

        }

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));


    }

    @Override
    public void onPlayerHit(Player p) {
        damage(p, 3, caster);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 100));
    }

    @Override
    public void onEntityHit(LivingEntity ent) {
        // TODO Auto-generated method stub

        damage(ent, 3, caster);
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 100));
    }

    @Override
    public void onSpellHit(Spell spell) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlockHit(Block block) {


        bounce();

    }


    @Override
    public void onDeath() {
        juan.remove();
        dave.remove();
    }


    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }


}
