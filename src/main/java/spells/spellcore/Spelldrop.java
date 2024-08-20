package spells.spellcore;

import esze.utils.NBTUtils;
import esze.utils.SoundUtils;
import esze.utils.SpellKeyUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.HashMap;

public class Spelldrop implements Listener {


    public static HashMap<ArmorStand, ArmorStand> items = new HashMap<ArmorStand, ArmorStand>();

    public Spelldrop() {
    }

    public Spelldrop(Location loc2) {

        Location loc = loc2.clone();
        //loc.add(0, 1, 0);
		
		/*ArmorStand a = (ArmorStand) Bukkit.getWorld("world").spawnEntity(loc, EntityType.ARMOR_STAND);
		a.setGravity(false);
		a.setVisible(false);
		a.setInvulnerable(true);
		a.setRightArmPose(EulerAngle.ZERO);
        a.setRightArmPose(a.getRightArmPose().add(0, 0.1, 0));
		a.setItemInHand(getRandomSpell());
		
		a.setCustomName(a.getItemInHand().getItemMeta().getDisplayName());
		
		ArmorStand name = (ArmorStand) Bukkit.getWorld("world").spawnEntity(loc.clone().add(0.30, -1.5, 0), EntityType.ARMOR_STAND);
		name.setGravity(false);
		name.setVisible(false);
		name.setInvulnerable(true);
		name.setCustomName(a.getItemInHand().getItemMeta().getDisplayName());
		name.setCustomNameVisible(true);*/


        ArmorStand as = loc.getWorld().spawn(loc.clone().add(0.30, -0.5, 0), ArmorStand.class);
        as.setGravity(false);
        as.setItemInHand(getRandomSpell());
        as.setBasePlate(false);
        as.setVisible(false);
        as.setMarker(false);
        as.getRightArmPose();
        as.setRightArmPose(EulerAngle.ZERO);
        as.setRightArmPose(as.getRightArmPose().add(0, 0.1, 0));

        ArmorStand holo = loc.getWorld().spawn(loc.clone().add(0.30, -1.5, 0), ArmorStand.class);
        holo.setCustomName(as.getItemInHand().getItemMeta().getDisplayName());
        holo.setCustomNameVisible(true);
        holo.setGravity(false);
        holo.setMarker(false);
        holo.setBasePlate(false);
        holo.setVisible(false);

        items.put(as, holo);
    }


    public static ItemStack getRandomSpell() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(SpellList.getRandomSpell().name);
        item.setItemMeta(im);
        item = NBTUtils.setNBT("Spell", "true", item);
        item = NBTUtils.setNBT("SpellKey", "" + SpellKeyUtils.getNextSpellKey(), item);
        return item;

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL)
            return;
        ArrayList<ArmorStand> delete = new ArrayList<ArmorStand>();
        for (ArmorStand a : items.keySet()) {

            if (a.getLocation().distance(p.getLocation()) < 1.5) {
                p.getInventory().addItem(a.getItemInHand());

                SoundUtils.playSound(Sound.ENTITY_ITEM_PICKUP, a.getLocation());
                delete.add(a);

            }
        }
        for (ArmorStand a : delete) {
            items.get(a).remove();
            items.remove(a);
            a.remove();
        }
        delete.clear();
    }

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

}
