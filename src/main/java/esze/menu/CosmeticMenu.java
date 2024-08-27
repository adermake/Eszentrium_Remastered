package esze.menu;

import esze.configs.PlayerSettingsService;
import esze.configs.entities.Cosmetic;
import esze.configs.entities.CosmeticType;
import esze.utils.CharRepo;
import esze.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        boolean highlightSlot = false;
        int testRunSlot = 9;
        PlayerSettingsService.PlayerSettings playerConfig = PlayerSettingsService.getPlayerSettings(p);
        for (Cosmetic cosmetic : categoryItems) {
            if (playerConfig.getCosmetic(cosmeticType) == cosmetic) {
                highlightSlot = true;
                break;
            }
            testRunSlot++;
        }

        String invName = "§f" + CharRepo.NEG8.literal + CharRepo.MENU_CONTAINER_45_BLACK.literal + CharRepo.getNeg(172);
        invName += tabToMenuName(tab);
        invName += CharRepo.getNeg(164);
        if (highlightSlot) invName += "§a" + getSlotHighlighter(testRunSlot) + CharRepo.getNeg(17);
        invName += "§8Kosmetik auswählen";

        Inventory inv = Bukkit.createInventory(null, 6 * 9, invName);
        for (CosmeticType category : CosmeticType.values()) {
            inv.addItem(category.createItem("§6" + category.getName(), null));
        }


        int slot = 9;
        for (Cosmetic cosmetic : categoryItems) {
            ItemStack i = cosmetic.createItem();
            i = NBTUtils.setNBT("CosmeticType", cosmeticType.name(), i);
            i = NBTUtils.setNBT("Cosmetic", cosmetic.name(), i);
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
                    Cosmetic cosmetic = Cosmetic.valueOf(NBTUtils.getNBT("Cosmetic", item));
                    PlayerSettingsService.getPlayerSettings(p).setCosmetic(cosmeticType, cosmetic);

                    String invName = e.getView().getTitle();
                    new CosmeticMenu(p, menuNameToTab(invName));
                }

            }
        }
    }

}
