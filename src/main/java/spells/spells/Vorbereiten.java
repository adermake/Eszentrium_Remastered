package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.utils.NBTUtils;
import spells.spellcore.Spell;
import spells.spellcore.SpellDescription;
import spells.spellcore.SpellList;
import spells.spellcore.SpellType;

public class Vorbereiten extends Spell {


    public Vorbereiten() {
        name = "§4Vorbereiten";
        spellDescription = new SpellDescription(
                "",
                "",
                null,
                null,
                null,
                null,
                0
        );
        addSpellType(SpellType.SUPPORT);

    }

    @Override
    public void setUp() {
        // TODO Auto-generated method stub

        String s = SpellList.getDiffrentRandomGreen(1).get(0).getName();

        s = "§2" + ChatColor.stripColor(s);
        final String s2 = s;
        new BukkitRunnable() {

            public void run() {
                ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(s2);
                is.setItemMeta(im);
                is = NBTUtils.setNBT("Spell", "true", is);
                caster.getInventory().setItemInMainHand(is);
            }
        }.runTaskLater(main.plugin, 1);

        dead = true;
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
