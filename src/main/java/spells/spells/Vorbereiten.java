package spells.spells;

import esze.main.main;
import esze.utils.NBTUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
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
        String s = SpellList.getDiffrentRandomGreen(1).getFirst().getName();

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

    }

    @Override
    public void launch() {

    }

    @Override
    public void move() {

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
