package esze.menu;

import esze.enums.GameType;
import esze.types.TypeTeamBased;
import esze.utils.EszeTeam;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamMonumenteSelectionMenu extends ItemMenu {

    public TeamMonumenteSelectionMenu(Player p) {
        super(1, "§eWähle dein Team");


        TypeTeamBased tt = (TypeTeamBased) GameType.getType();

        if (tt.allTeams.size() == 4) {
            int i = 2;
            for (EszeTeam t : tt.allTeams) {
                if (t.containsPlayer(p)) {
                    addClickableItem(i, 1, t.getTeamIcon(), t.getTeamName(), null, true);
                } else {
                    addClickableItem(i, 1, t.getTeamIcon(), t.getTeamName());
                }

                i += 2;
            }
        }
        if (tt.allTeams.size() == 2) {
            int i = 3;
            for (EszeTeam t : tt.allTeams) {
                if (t.containsPlayer(p)) {
                    addClickableItem(i, 1, t.getTeamIcon(), t.getTeamName(), null, true);
                } else {
                    addClickableItem(i, 1, t.getTeamIcon(), t.getTeamName());
                }

                i += 4;
            }
        }


        // TODO Auto-generated constructor stub
    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p) {
        // TODO Auto-generated method stub
        if (GameType.getType() instanceof TypeTeamBased) {
            TypeTeamBased tt = (TypeTeamBased) GameType.getType();
            for (EszeTeam t : tt.allTeams) {
                if (t.getTeamName().equals(icon.getItemMeta().getDisplayName())) {
                    tt.removePlayerFromAllTeams(p);
                    t.addPlayer(p);
                    ItemMeta im = icon.getItemMeta();
                    im.addEnchant(Enchantment.LOYALTY, 1, true);
                    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    icon.setItemMeta(im);
                    p.closeInventory();

                }
            }
        }


    }

    @Override
    public void clicked(ItemMenuIcon icon, Player p, InventoryAction a) {
        // TODO Auto-generated method stub

    }

}
