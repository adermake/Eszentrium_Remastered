package esze.menu;

import esze.enums.GameType;
import esze.types.TypeTEAMS;
import esze.types.TypeTeamBased;
import esze.utils.CharRepo;
import esze.utils.EszeTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public class TeamSelectionMenu{


    private static String getMenuTitleForPlayer(Player p) {
        CharRepo cr = CharRepo.MENU_CONTAINER_45_TEAM;

        TypeTeamBased tt = (TypeTeamBased) GameType.getType();
        Optional<CharRepo> teamColor = tt.allTeams.stream()
                .filter(t -> t.containsPlayer(p))
                .map(EszeTeam::getChatColor)
                .map(color -> switch (color) {
                    case RED -> CharRepo.MENU_CONTAINER_45_TEAM_RED;
                    case BLUE -> CharRepo.MENU_CONTAINER_45_TEAM_BLUE;
                    case GREEN -> CharRepo.MENU_CONTAINER_45_TEAM_GREEN;
                    case YELLOW -> CharRepo.MENU_CONTAINER_45_TEAM_YELLOW;
                    default -> CharRepo.MENU_CONTAINER_45_TEAM;
                })
                .findFirst();

        if(teamColor.isPresent()) {
            cr = teamColor.get();
        }

        return CharRepo.getNeg(8) + "§f" + cr;
    }

    public TeamSelectionMenu(Player p) {
        TypeTeamBased tt = (TypeTeamBased) GameType.getType();
        ChatColor teamColor = tt.allTeams.stream()
                .filter(t -> t.containsPlayer(p))
                .map(EszeTeam::getChatColor).findFirst().orElse(null);

        Inventory inv = Bukkit.createInventory(null, 5 * 9, getMenuTitleForPlayer(p));
        ItemStack selectItem = produceFillerItem("§7Wählen");
        if(teamColor != ChatColor.RED) {
            inv.setItem(2, selectItem);
            inv.setItem(3, selectItem);
            inv.setItem(11, selectItem);
            inv.setItem(12, selectItem);
        }
        if(teamColor != ChatColor.BLUE) {
            inv.setItem(5, selectItem);
            inv.setItem(6, selectItem);
            inv.setItem(14, selectItem);
            inv.setItem(15, selectItem);
        }
        if(teamColor != ChatColor.GREEN) {
            inv.setItem(29, selectItem);
            inv.setItem(30, selectItem);
            inv.setItem(38, selectItem);
            inv.setItem(39, selectItem);
        }
        if(teamColor != ChatColor.YELLOW) {
            inv.setItem(32, selectItem);
            inv.setItem(33, selectItem);
            inv.setItem(41, selectItem);
            inv.setItem(42, selectItem);
        }

        p.openInventory(inv);
    }

    private ItemStack produceFillerItem(String name) {
        ItemStack filler = new ItemStack(Material.PAPER);
        ItemMeta meta = filler.getItemMeta();
        meta.setCustomModelData(1000303);
        meta.setDisplayName(name);
        filler.setItemMeta(meta);
        return filler;
    }

    public static void onClickEvent(InventoryClickEvent e) {
        if (GameType.getType() instanceof TypeTEAMS tt) {
            String invTitle = e.getView().getTitle();
            if(invTitle.contains(CharRepo.MENU_CONTAINER_45_TEAM_RED.literal) ||
                    invTitle.contains(CharRepo.MENU_CONTAINER_45_TEAM_BLUE.literal) ||
                    invTitle.contains(CharRepo.MENU_CONTAINER_45_TEAM_GREEN.literal) ||
                    invTitle.contains(CharRepo.MENU_CONTAINER_45_TEAM_YELLOW.literal) ||
                    invTitle.contains(CharRepo.MENU_CONTAINER_45_TEAM.literal)) {
                e.setCancelled(true);
                ChatColor teamColorClicked = switch(e.getSlot()) {
                    case 2, 3, 11, 12 -> ChatColor.RED;
                    case 5, 6, 14, 15 -> ChatColor.BLUE;
                    case 29, 30, 38, 39 -> ChatColor.GREEN;
                    case 32, 33, 41, 42 -> ChatColor.YELLOW;
                    default -> null;
                };

                if(teamColorClicked != null) {
                    EszeTeam team = tt.allTeams.stream()
                            .filter(t -> t.getChatColor() == teamColorClicked)
                            .findFirst()
                            .orElse(null);
                    if(team != null) {
                        Player p = (Player) e.getWhoClicked();
                        tt.removePlayerFromAllTeams(p);
                        team.addPlayer(p);
                        new TeamSelectionMenu(p);
                        tt.givePlayerLobbyItems(p);
                    }
                }
            }
        }


    }

}

