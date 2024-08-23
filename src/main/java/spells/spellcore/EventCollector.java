package spells.spellcore;

import esze.analytics.SaveUtils;
import esze.main.main;
import esze.menu.GameModifier;
import esze.menu.ModifierMenu;
import esze.utils.NBTUtils;
import esze.utils.SoundUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import weapons.WeaponAbilitys;

import java.util.ArrayList;
import java.util.HashMap;

public class EventCollector implements Listener {

    public static HashMap<Player, Inventory> openInventory = new HashMap<Player, Inventory>();

    @EventHandler
    public void onPressSpell(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			
		/*
			if (Spell.silenced.contains(p)) {
				Actionbar bar = new Actionbar("§cDu bist verstummt!");
				return;
			}
			*/
            ItemStack is = p.getInventory().getItemInMainHand();
            boolean refined = false;
            boolean traitor = false;
            if (is != null) {
                if (is.hasItemMeta()) {
                    if (is.getItemMeta().hasDisplayName()) {

                        if (is.getItemMeta().getDisplayName().equals("§7Verbranntes Buch")) {
                            SoundUtils.playSound(Sound.ENTITY_GENERIC_BURN, p.getLocation(), 1, 0.5F);
                            is.setAmount(0);
                            return;
                        }
                        if (!NBTUtils.getNBT("Cooldown", is).equals("")) {
                            if (Double.parseDouble(NBTUtils.getNBT("Cooldown", is)) > 0) {

                                return;
                            }

                            if (!NBTUtils.getNBT("Spell", is).equals("true")) {
                                return;
                            }

                            String name = is.getItemMeta().getDisplayName();
                            if (name.contains("§2")) {

                                refined = true;
                            }

                            name = name.substring(2, name.length());
                            name = name.replace(" ", "");
                            try {
                                name = "spells.spells." + name;
                                // Bukkit.broadcastMessage("F" + s);
                                Class clazz = Class.forName(name);
                                Spell sp = (Spell) clazz.newInstance();

                                sp.refined = refined;
                                if (!sp.traitorSpell) {


                                    is = NBTUtils.setNBT("Cooldown", "" + sp.getSpellDescription().getCooldown() + "", is);
                                    is = NBTUtils.setNBT("MaxCooldown", "" + sp.getSpellDescription().getCooldown(), is);
                                    is = NBTUtils.setNBT("OriginalName", is.getItemMeta().getDisplayName(), is);


                                }
                                SaveUtils.addSpellUse(p.getName(), sp.getName(), refined);
                                sp.applySpellKey(p);
                                if (sp.castSpell(p, is.getItemMeta().getDisplayName())) {
                                    is = NBTUtils.setNBT("Cooldown", "" + "0" + "", is);
                                }

                                if (!sp.traitorSpell)
                                    WeaponAbilitys.lastLaunched.put(p, name);

                                if (sp.traitorSpell && !sp.refund) {

                                    is = NBTUtils.setNBT("Burn", "true", is);
                                }

                                doubleShot(p, sp, refined);
                                p.getInventory().setItemInMainHand(is);

                                randomSpell(p, is, refined);

                            } catch (Exception ex) {
                                ex.printStackTrace(System.out);
                            }
                            ;

                        } else {

                            if (!NBTUtils.getNBT("Spell", is).equals("true")) {
                                return;
                            }
                            String name = is.getItemMeta().getDisplayName();
                            if (name.contains("§2")) {
                                refined = true;
                            }

                            name = name.substring(2, name.length());

                            name = name.replace(" ", "");
                            try {
                                name = "spells.spells." + name;
                                // Bukkit.broadcastMessage("F" + s);
                                Class clazz = Class.forName(name);
                                Spell sp = (Spell) clazz.newInstance();


                                sp.refined = refined;
                                is = NBTUtils.setNBT("Cooldown", "" + sp.getSpellDescription().getCooldown() + "", is);
                                is = NBTUtils.setNBT("MaxCooldown", "" + sp.getSpellDescription().getCooldown(), is);
                                is = NBTUtils.setNBT("OriginalName", is.getItemMeta().getDisplayName(), is);
                                if (sp.castSpell(p, is.getItemMeta().getDisplayName())) {
                                    is = NBTUtils.setNBT("Cooldown", "" + "0" + "", is);
                                }
                                sp.applySpellKey(p);
                                SaveUtils.addSpellUse(p.getName(), sp.getName(), refined);
                                if (!sp.traitorSpell)
                                    WeaponAbilitys.lastLaunched.put(p, name);

                                if (sp.traitorSpell && !sp.refund) {

                                    is = NBTUtils.setNBT("Burn", "true", is);
                                }

                                p.getInventory().setItemInMainHand(is);
                                doubleShot(p, sp, refined);
                                randomSpell(p, is, refined);


                            } catch (Exception ex) {
                                //ex.printStackTrace(System.out);
                                p.sendMessage("Spell is not vaild!");
                                p.sendMessage(ex.toString());
                            }
                            ;
                        }


                    }

                }

            }
        }


    }


    public void doubleShot(Player p, Spell s, boolean refined) {

        if (ModifierMenu.hasModifier(GameModifier.DOPPELSCHUSS)) {
            new BukkitRunnable() {
                public void run() {
                    Class clazz = s.getClass();
                    try {
                        Spell sp = (Spell) clazz.newInstance();
                        sp.refined = refined;
                        sp.castSpell(p, sp.getName());

                    } catch (InstantiationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }.runTaskLater(main.plugin, 20);
        }
    }


    public void randomSpell(Player p, ItemStack is, boolean refined) {
        if (ModifierMenu.hasModifier(GameModifier.ZUFALLSZAUBER)) {


            Spell sp = SpellList.getRandomSpell();
            if (refined) {
                sp = SpellList.getDiffrentRandomGreen(1).get(0);
            }
            is = NBTUtils.setNBT("Cooldown", "" + sp.getSpellDescription().getCooldown() + "", is);
            is = NBTUtils.setNBT("MaxCooldown", "" + sp.getSpellDescription().getCooldown(), is);
            is = NBTUtils.setNBT("OriginalName", sp.getName(), is);

            p.getInventory().setItemInMainHand(is);
        }
    }

    public static ArrayList<Player> quickSwap = new ArrayList<Player>();

    @EventHandler
    public void onPayRespectF(PlayerSwapHandItemsEvent e) {
        if (!quickSwap.contains(e.getPlayer())) {
            quickSwap.add(e.getPlayer());
        }

        Spell.pressingF.add(e.getPlayer());
        e.setCancelled(true);
    }
	/*
	@EventHandler
	public void onOpenInventory(InventoryClickEvent e) {
		openInventory.put((Player)e.getWhoClicked(), e.getView().getTopInventory());
		//Bukkit.broadcastMessage(""+ "NO BUGG");
	}
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		openInventory.remove(e.getPlayer());
	}
	
	*/

    @EventHandler
    public void plsDontLeave(PlayerToggleSneakEvent e) {
        if (e.getPlayer().getVehicle() != null) {
            e.setCancelled(true);
        }

    }


    @EventHandler
    public void onTarget(EntityTargetEvent e) {

        e.setCancelled(true);

    }

    @EventHandler
    public void onSolidify(EntityChangeBlockEvent e) {

        e.getEntity().remove();
        e.setCancelled(true);
    }

    @EventHandler
    public void cancelGilding(EntityToggleGlideEvent e) {
        if (Spell.gliding.contains(e.getEntity())) {
            e.setCancelled(true);
        }
    }

}
