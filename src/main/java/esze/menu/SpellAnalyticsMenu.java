package esze.menu;

import esze.analytics.SaveUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import spells.spellcore.Spell;
import spells.spellcore.SpellList;

import java.util.ArrayList;

public class SpellAnalyticsMenu extends ItemMenu {

    public SpellAnalyticsMenu(String colorTag, String p) {
        super(6, colorTag + "Spells    ");
        // TODO Auto-generated constructor stub
        int x = 1;
        int y = 1;
        for (Spell s : SpellList.getSortedSpells()) {
            if (!s.getName().contains(colorTag)) {
                continue;
            }
            ArrayList<String> lore = new ArrayList<String>();
            String number = "§e";
            String text = "§7";
            lore.add(text + "Allgemeine Tötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKills(SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Allgemeine SchadensTötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKillsNormal(SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Allgemeine VoidTötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKillsVoid(SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine Tötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKills(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine SchadensTötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKillsNormal(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine VoidTötungen: " + number + ((int) SaveUtils.getAnalytics().getSpellKillsVoid(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine Tode: " + number + ((int) SaveUtils.getAnalytics().getSpellDeaths(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine SchadensTode: " + number + ((int) SaveUtils.getAnalytics().getSpellDeathsNormal(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine VoidTode: " + number + ((int) SaveUtils.getAnalytics().getSpellDeathsVoid(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine Auswahlrate: " + number + cut(SaveUtils.getAnalytics().getSpellWorth(p, SaveUtils.rmColor(s.getName()))) + "%");
            //lore.add( text + "Deine verbesserte Auswahlrate: "  		+ number + cut(SaveUtils.getAnalytics().getEnhancedSpellWorth(p, SaveUtils.rmColor(s.getName())) ) + "%" );
            lore.add(text + "Allgemeine Auswahlrate: " + number + cut(SaveUtils.getAnalytics().getWorth(SaveUtils.rmColor(s.getName()))) + "%");
            lore.add(text + "Allgemeine Nutzungs Offensive Fähigkeit: " + number + cut(SaveUtils.getAnalytics().getUseWorth(SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine Nutzungs Offensive Fähigkeit: " + number + cut(SaveUtils.getAnalytics().getUseWorth(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Deine Offensive Fähigkeit: " + number + cut(SaveUtils.getAnalytics().getWorthOffensive(p, SaveUtils.rmColor(s.getName()))));
            lore.add(text + "Allgemeine Offensive Fähigkeit: " + number + cut(SaveUtils.getAnalytics().getWorthOffensive(SaveUtils.rmColor(s.getName()))));
            //lore.add( text + "Allgemeine verbesserte Auswahlrate: "  	+ number + cut(SaveUtils.getAnalytics().getEnhancedWorth(SaveUtils.rmColor(s.getName())) ) + "%" );
            addClickableItem(x, y, Material.BOOK, s.getName(), lore);
            x++;
            if (x > 9) {
                y++;
                x = 1;
            }

        }

        for (Spell s : SpellList.traitorSpells) {
            if (!s.getName().contains(colorTag))
                continue;
            addClickableItem(x, y, Material.BOOK, s.getName());
            x++;
            if (x > 9) {
                y++;
                x = 1;
            }

        }
    }

    public String cut(double d) {
        String s = "" + d;
        int max = 4;
        if (s.length() > max) {
            s = s.subSequence(0, max).toString();
        }
        return s;
    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
        // TODO Auto-generated method stub

    }

}
