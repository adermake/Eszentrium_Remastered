package esze.menu;

import esze.configs.PlayerSettingsGuy;
import esze.configs.PlayerSettingsService;
import esze.configs.entities.Cosmetic;
import esze.configs.entities.CosmeticType;
import esze.utils.CharRepo;
import esze.utils.ItemStackUtils;
import esze.utils.NBTUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CosmeticMenu {

    private static String tabToMenuName(int tab) {
        return switch (tab) {
            default -> CharRepo.MENU_TAB_1.literal;
            case 2 -> CharRepo.MENU_TAB_2.literal;
            case 3 -> CharRepo.MENU_TAB_3.literal;
            case 4 -> CharRepo.MENU_TAB_4.literal;
            case 5 -> CharRepo.MENU_TAB_5.literal;
            case 6 -> CharRepo.MENU_TAB_6.literal;
            case 7 -> CharRepo.MENU_TAB_7.literal;
            case 8 -> CharRepo.MENU_TAB_8.literal;
            case 9 -> CharRepo.MENU_TAB_9.literal;
        };
    }

    private static String getSlotHighlighter(int slot) {
        String highlighterText = "";
        int column = slot % 9;
        int row = slot / 9;

        for (int i = 0; i < column; i++) {
            highlighterText += CharRepo.getPos(18);
        }

        highlighterText += switch (row) {
            case 0 -> CharRepo.MENU_SLOT_FILL_ROW_1.literal;
            case 1 -> CharRepo.MENU_SLOT_FILL_ROW_2.literal;
            case 2 -> CharRepo.MENU_SLOT_FILL_ROW_3.literal;
            case 3 -> CharRepo.MENU_SLOT_FILL_ROW_4.literal;
            case 4 -> CharRepo.MENU_SLOT_FILL_ROW_5.literal;
            case 5 -> CharRepo.MENU_SLOT_FILL_ROW_6.literal;
            default -> "";
        };

        for (int i = 0; i < column; i++) {
            highlighterText += CharRepo.getNeg(18);
        }

        return highlighterText;
    }

    private static int menuNameToTab(String menuName) {
        if (menuName.contains(CharRepo.MENU_TAB_1.literal)) {
            return 1;
        } else if (menuName.contains(CharRepo.MENU_TAB_2.literal)) {
            return 2;
        } else if (menuName.contains(CharRepo.MENU_TAB_3.literal)) {
            return 3;
        } else if (menuName.contains(CharRepo.MENU_TAB_4.literal)) {
            return 4;
        } else if (menuName.contains(CharRepo.MENU_TAB_5.literal)) {
            return 5;
        } else if (menuName.contains(CharRepo.MENU_TAB_6.literal)) {
            return 6;
        } else if (menuName.contains(CharRepo.MENU_TAB_7.literal)) {
            return 7;
        } else if (menuName.contains(CharRepo.MENU_TAB_8.literal)) {
            return 8;
        } else if (menuName.contains(CharRepo.MENU_TAB_9.literal)) {
            return 9;
        }
        return 1;
    }

    public static CosmeticType tabToCosmeticType(int tab) {
        return CosmeticType.values()[tab - 1];
    }


    public CosmeticMenu(Player p, int tab) {
        CosmeticType cosmeticType = tabToCosmeticType(tab);
        List<Cosmetic> categoryItems = Cosmetic.getCosmeticsByType(cosmeticType);

        PlayerSettingsService.PlayerSettings playerConfig = PlayerSettingsService.getPlayerSettings(p);
        int testRunSlot = 9;
        if (playerConfig.getCosmetic(cosmeticType) != null) {
            if(cosmeticType != CosmeticType.WEAPON) {
                testRunSlot++;
            }
            testRunSlot += Arrays.stream(Cosmetic.values()).filter(c -> c.getType() == cosmeticType).toList().indexOf(playerConfig.getCosmetic(cosmeticType));
        }

        String invName = "§f" + CharRepo.NEG8.literal + CharRepo.MENU_CONTAINER_45_BLACK.literal + CharRepo.getNeg(172);
        invName += tabToMenuName(tab);
        invName += CharRepo.getNeg(164);
        invName += "§a" + getSlotHighlighter(testRunSlot) + CharRepo.getNeg(17);
        invName += "§8Kosmetik auswählen";

        Inventory inv = Bukkit.createInventory(null, 6 * 9, invName);
        for (CosmeticType category : CosmeticType.values()) {
            inv.addItem(category.createItem("§6" + category.getName(), null));
        }


        int slot = 9;
        if (cosmeticType != CosmeticType.WEAPON) {
            ItemStack i = ItemStackUtils.createItemStack(Material.BARRIER, 1, 0, "§r§fKeine Auswahl", null, false);
            i = NBTUtils.setNBT("CosmeticType", cosmeticType.name(), i);
            inv.setItem(slot, i);
            slot++;
        }
        for (Cosmetic cosmetic : categoryItems) {
            ItemStack i = cosmetic.createItem();
            i = NBTUtils.setNBT("CosmeticType", cosmeticType.name(), i);
            i = NBTUtils.setNBT("Cosmetic", cosmetic.name(), i);
            if(cosmeticType == CosmeticType.WEAPON) {
                i = ItemStackUtils.attackSpeedify(i);
                i = ItemStackUtils.attackDamage(i, 4);
            } else {
                i = ItemStackUtils.removeArmorToughness(i);
            }
            inv.setItem(slot, i);
            slot++;
        }

        p.openInventory(inv);
    }


    public static void onClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String invTitle = e.getView().getTitle();
        if (invTitle.contains("Kosmetik auswählen")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (e.getSlot() < 9) {
                    new CosmeticMenu(p, e.getSlot() + 1);
                } else {
                    ItemStack item = e.getCurrentItem();
                    CosmeticType cosmeticType = CosmeticType.valueOf(NBTUtils.getNBT("CosmeticType", item));
                    if (item.getType() == Material.BARRIER) {
                        PlayerSettingsService.getPlayerSettings(p).setCosmetic(cosmeticType, null);
                    } else {
                        Cosmetic cosmetic = Cosmetic.valueOf(NBTUtils.getNBT("Cosmetic", item));
                        PlayerSettingsService.getPlayerSettings(p).setCosmetic(cosmeticType, cosmetic);
                    }
                    PlayerSettingsGuy.updatePlayerSettingsGuy(p);
                    p.playSound(p.getLocation(), Sound.ENTITY_WITCH_THROW, 1, 1);

                    Location guyLocation = PlayerSettingsGuy.getPlayerSettingsGuyLocation(p);
                    if(guyLocation != null) {
                        guyLocation.add(0, 1, 0);
                        p.spawnParticle(Particle.HEART, guyLocation, 6, 0.5, 0.5, 0.5, 0);
                        p.spawnParticle(Particle.WHITE_SMOKE, guyLocation, 100, 0.5, 1, 0.5, 0);
                    }

                    String invName = e.getView().getTitle();
                    new CosmeticMenu(p, menuNameToTab(invName));
                }

            }
        }
    }

}
