package esze.menu;

import esze.utils.CharRepo;
import esze.utils.PlayerConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CosmeticMenu {

    private final HashMap<ItemStack, List<ItemStack>> categories = new HashMap<>();

    private void fillCategories() {
        categories.put(createItem(Material.DIAMOND_SWORD, "§6Schwerter", null, null),
                List.of(
                        new ItemStack(Material.WOODEN_SWORD),
                        new ItemStack(Material.GOLDEN_SWORD),
                        new ItemStack(Material.STONE_SWORD),
                        new ItemStack(Material.IRON_SWORD),
                        new ItemStack(Material.DIAMOND_SWORD),
                        new ItemStack(Material.NETHERITE_SWORD),
                        createItem(Material.WOODEN_SWORD, "§r§fRotes Schwert", 1, null)
                )
        );

        categories.put(createItem(Material.DIAMOND_PICKAXE, "§6Werkzeug", null, null),
                List.of(
                        new ItemStack(Material.WOODEN_AXE),
                        new ItemStack(Material.WOODEN_PICKAXE),
                        new ItemStack(Material.WOODEN_HOE),
                        new ItemStack(Material.WOODEN_SHOVEL),
                        new ItemStack(Material.GOLDEN_AXE),
                        new ItemStack(Material.GOLDEN_PICKAXE),
                        new ItemStack(Material.GOLDEN_HOE),
                        new ItemStack(Material.GOLDEN_SHOVEL),
                        new ItemStack(Material.STONE_AXE),
                        new ItemStack(Material.STONE_PICKAXE),
                        new ItemStack(Material.STONE_HOE),
                        new ItemStack(Material.STONE_SHOVEL),
                        new ItemStack(Material.IRON_AXE),
                        new ItemStack(Material.IRON_PICKAXE),
                        new ItemStack(Material.IRON_HOE),
                        new ItemStack(Material.IRON_SHOVEL),
                        new ItemStack(Material.DIAMOND_AXE),
                        new ItemStack(Material.DIAMOND_PICKAXE),
                        new ItemStack(Material.DIAMOND_HOE),
                        new ItemStack(Material.DIAMOND_SHOVEL),
                        new ItemStack(Material.NETHERITE_AXE),
                        new ItemStack(Material.NETHERITE_PICKAXE),
                        new ItemStack(Material.NETHERITE_HOE),
                        new ItemStack(Material.NETHERITE_SHOVEL)
                )
        );

        categories.put(createItem(Material.FIREWORK_ROCKET, "§6Sonstiges", null, null),
                List.of(
                        createItem(Material.WOODEN_SWORD, "§r§fStock", 2, null),
                        new ItemStack(Material.BLAZE_ROD),
                        new ItemStack(Material.BONE),
                        new ItemStack(Material.BAMBOO),
                        new ItemStack(Material.CARROT_ON_A_STICK)
                )
        );
    }

    private ItemStack createItem(Material material, String displayName, Integer customModelData, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
        return item;
    }

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


    public CosmeticMenu(Player p, int tab) {
        fillCategories();
        List<ItemStack> categoryItems = categories.get(categories.keySet().toArray()[tab - 1]);

        boolean highlightSlot = false;
        int testRunSlot = 9;
        PlayerConfig playerConfig = PlayerConfig.getConfig(p);
        for (ItemStack i : categoryItems) {
            Material iMaterial = i.getType();
            String iDisplayName = i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : null;
            Integer iCustomModelData = i.hasItemMeta() && i.getItemMeta().hasCustomModelData() ? i.getItemMeta().getCustomModelData() : null;
            if (playerConfig.getWeaponMaterial() == iMaterial) {
                if(Objects.equals(playerConfig.getWeaponCustomName(), iDisplayName)) {
                    if(Objects.equals(playerConfig.getWeaponCustomModelData(), iCustomModelData)) {
                        highlightSlot = true;
                        break;
                    }
                }
            }
            testRunSlot++;
        }

        String invName = "§f" + CharRepo.NEG8.literal + CharRepo.MENU_CONTAINER_45_BLACK.literal + CharRepo.getNeg(172);
        invName += tabToMenuName(tab);
        invName += CharRepo.getNeg(164);
        if(highlightSlot) invName += "§a" + getSlotHighlighter(testRunSlot) + CharRepo.getNeg(17);
        invName += "§8Kosmetik auswählen";

        Inventory inv = Bukkit.createInventory(null, 6 * 9, invName);
        for (ItemStack category : categories.keySet()) {
            inv.addItem(category);
        }


        int slot = 9;
        for (ItemStack i : categoryItems) {
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
                    String name = null;
                    Integer customModelData = null;
                    if (item.hasItemMeta()) {
                        ItemMeta im = item.getItemMeta();
                        if (im.hasDisplayName()) {
                            name = im.getDisplayName();
                        }
                        if (im.hasCustomModelData()) {
                            customModelData = im.getCustomModelData();
                        }
                    }
                    PlayerConfig.getConfig(p).setWeapon(item.getType(), customModelData, name);
                    String invName = e.getView().getTitle();
                    new CosmeticMenu(p, menuNameToTab(invName));
                }

            }
        }
    }

}
