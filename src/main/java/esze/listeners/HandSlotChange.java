package esze.listeners;

import esze.utils.BossbarSpellHUD;
import esze.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

import java.util.Optional;

public class HandSlotChange implements Listener {

    @EventHandler
    public void onHandSlotChange(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        ItemStack is = p.getInventory().getItem(e.getNewSlot());
        if(is != null && (is.getType() == Material.ENCHANTED_BOOK || is.getType() == Material.BOOK) && is.hasItemMeta()) {
            if (NBTUtils.getNBT("Spell", is).equals("true")) {
                String originalIsName = ChatColor.stripColor(NBTUtils.getNBT("OriginalName", is));
                Optional<Spell> spellOpt = SpellList.spells.keySet().stream()
                        .filter(s -> originalIsName.toLowerCase().replace(" ", "").startsWith(ChatColor.stripColor(s.getName()).toLowerCase().replace(" ", "")))
                        .findFirst();

                if(spellOpt.isPresent()) {
                    Spell spell = spellOpt.get();
                    new BossbarSpellHUD(p, spell.getName(), "TEST", "TEST").show();
                    return;
                }
            }
        }
        BossbarSpellHUD.removeAllBossbars(p);
    }
}
