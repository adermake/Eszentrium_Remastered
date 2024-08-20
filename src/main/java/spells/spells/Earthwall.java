package spells.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import esze.objects.EszeFace;
import esze.objects.EszeObject;
import esze.objects.ObjectUtils;
import esze.utils.ParUtils;

import org.bukkit.Particle;
import spells.spellcore.Spell;

public class Earthwall extends Spell {


    ArrayList<EszeObject> objs;
    ArrayList<Entity> fb = new ArrayList<Entity>();

    @Override
    public void setUp() {
        Bukkit.broadcastMessage("go");
        objs = ObjectUtils.loadObjects("Hand", 0.222);
        for (EszeObject eo : objs) {
            eo.scale(1.3F);
        }

        for (int i = 0; i < objs.get(0).vert.size(); i += 1) {

            ArmorStand f = createArmorStand(loc.clone().add(randVector()));
            f.setHelmet(new ItemStack(Material.WHITE_CONCRETE));

            f.setSmall(true);
            f.setMarker(false);
            f.setGravity(true);
            fb.add(f);

        }

        speed = 0.5F;
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

    int i = 0;
    double d = 0;

    @Override
    public void display() {
        // TODO Auto-generated method stub
        //Bukkit.broadcastMessage(""+vert1.isEmpty());
        i++;
        if (i >= objs.size()) {
            i = 0;
        }

        int index = 0;
        boolean skip = false;

        //EszeObject eo = ObjectUtils.loadObject("Hand/handanim_000001", d);
        if (caster.isSneaking()) {

            if (caster.isSneaking()) {
                loc.add(0, -3, 0);
            }
        }
        if (loc.getY() < 130) {
            if (!caster.isSneaking()) {
                loc.add(0, 1, 0);
            }
        }

        Bukkit.broadcastMessage("" + d);
        for (Vector v : objs.get(i).vert) {


            doPin(fb.get(index), loc.clone().add(v), 2);
            //fb.get(index).teleport(loc.clone().add(v));
            //fb.get(index).setPortalCooldown(0);
            //ParUtils.createParticle(Particle.WATER_BUBBLE, loc.clone().add(v), 0, 0, 0,1, 0);
            //ParUtils.createParticle(Particle.WATER_BUBBLE, loc.clone().add(v), 0, 0, 0, 1, 1);
            index++;
        }


        //

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
