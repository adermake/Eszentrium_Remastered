package spells.spells;

import org.bukkit.block.Block;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellType;

public class UntotePhalanx extends Spell {
    public UntotePhalanx() {
        name = "§cUntote Phalanx";
        speed = 5;
        hitboxSize = 2;
        hitPlayer = true;
        hitSpell = true;

        spellDescription = new SpellDescription(
                "Schießt ein Projektil in Blickrichtung, das getroffene Gegner zurückwirft.",
                "Schießt ein Projektil in Blickrichtung, das getroffene Gegner zurückwirft.",
                null,
                null,
                null,
                null,
                20*40
        );
        
        addSpellType(SpellType.KNOCKBACK);
        addSpellType(SpellType.PROJECTILE);
        steprange = 120;

    }

    Horse juan;

    @Override
    public void setUp() {
        // TODO Auto-generated method stub
        Vector v = caster.getLocation().getDirection().crossProduct(new Vector(0, 1, 0)).normalize();
        Vector h = v.clone().crossProduct(new Vector(0, 1, 0)).normalize();
        new Juan(caster, name, new Vector(0, 0, 0));
        new Juan(caster, name, v.clone().multiply(2).add(h.clone().multiply(2)));
        new Juan(caster, name, v.clone().multiply(-2).add(h.clone().multiply(2)));
        new Juan(caster, name, v.clone().multiply(4).add(h.clone().multiply(4)));
        new Juan(caster, name, v.clone().multiply(-4).add(h.clone().multiply(4)));
    }

    @Override
    public void cast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {

    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //ParUtils.createFlyingParticle(Particle.BUBBLE_POP, loc,0, 0, 0, 1, 2, loc.getDirection().multiply(-1));


    }

    @Override
    public void onPlayerHit(Player p) {

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


    }


    @Override
    public void onDeath() {

    }


    @Override
    public void launch() {
        // TODO Auto-generated method stub

    }


}
