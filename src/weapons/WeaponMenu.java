package weapons;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import esze.main.main;
import esze.menu.ItemMenu;
import esze.menu.ItemMenuIcon;
import esze.utils.Actionbar;
import esze.utils.ItemStackUtils;

public class WeaponMenu extends ItemMenu{
	
	public static HashMap<Player,String> items = new  HashMap<Player,String>();
	
	public WeaponMenu(Player p) {
		super(1, "§cArsenal");
		
		if (items.containsKey(p)) {
			int count = 1;
			for (String name : WeaponList.weapons.keySet()) {
				addClickableItem(count, 1, WeaponList.weapons.get(name), name ,"§e---",items.get(p).contains(removeColorTag(name)));
				count++;
			}
			
			/*
			addClickableItem(2, 1, Material.BOW, "§cBogen","§e---",items.get(p).contains("Bogen"));
			addClickableItem(3, 1, Material.HEART_OF_THE_SEA, "§cFokussphäre","§e---",items.get(p).contains("Fokussphäre"));*/
		}
		else {
			
			int count = 1;
			for (String name : WeaponList.weapons.keySet()) {
				addClickableItem(count, 1, WeaponList.weapons.get(name), name ,"§e---");
				count++;
			}
			/*
			addClickableItem(1, 1, Material.WOODEN_SWORD, "§cSchwert","§e---");
			addClickableItem(2, 1, Material.BOW, "§cBogen","§e---");
			addClickableItem(3, 1, Material.HEART_OF_THE_SEA, "§cFokussphäre","§e---");*/
		}
		
		
	}
	
	public static String removeColorTag(String s) {
		for (String tag : main.colorTags) {
			s = s.replace(tag, "");
		}
		return s;
	}

	
	/*
	public static int calcTraitorMenuSize() {
		int spellCount = SpellList.traitorSpells.size();
		int size = 0;
		
		while (size<spellCount) {
			size+=9;
		}
		return size;
	}
	*/
	
	@Override
	public void clicked(ItemMenuIcon icon, Player p) {
		
		items.put(p, icon.getName());
		p.sendMessage("Du hast " +icon.getName() +"§r ausgewählt!");
		ItemMeta im = icon.getItemMeta();
		im.addEnchant(Enchantment.LURE, 1, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		icon.setItemMeta(im);
		p.closeInventory();
		
	}
	
	public static boolean running = true;
	public static void deliverItems() {
		running = true;
		WeaponAbilitys.cd.clear();
		WeaponAbilitys.cd2.clear();
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			
			WeaponAbilitys.charge1.put(p, 0);
			WeaponAbilitys.charge2.put(p, 0);
			/*
			if (items.get(p).contains(removeColorTag(WeaponList.BOWNAME))) {
				ItemStack is = new ItemStack(Material.BOW);
				ItemMeta im = is.getItemMeta();
				im.setUnbreakable(true);
				is.setItemMeta(im);
				p.getInventory().addItem(is);
			
				
				new BukkitRunnable() {
					int i = 0;
					int time = 0;
					@Override
					public void run() {
						
						String text = "§cSammlung: §e" +WeaponAbilitys.charge1.get(p)+"§r --- "+ "§cKöcher §e" +WeaponAbilitys.charge2.get(p);
						if (!running) {
							this.cancel();
						}
						
						
						if (time > 3) {
							
							WeaponAbilitys.charge1.put(p, WeaponAbilitys.charge1.get(p)-1);
							WeaponAbilitys.charge2.put(p, WeaponAbilitys.charge2.get(p)+1);
							time = 0;
						}
						
						if (p.isSneaking() && WeaponAbilitys.charge1.containsKey(p) && WeaponAbilitys.charge1.get(p)>0) {
							if (time == 0)
								text = "§cSammlung: " +WeaponAbilitys.charge1.get(p)+" §r>>> "+ "§cKöcher " +WeaponAbilitys.charge2.get(p);
							if (time == 1)
								text = "§cSammlung: " +WeaponAbilitys.charge1.get(p)+" §r)>> "+ "§cKöcher " +WeaponAbilitys.charge2.get(p);
							if (time == 2)
								text = "§cSammlung: " +WeaponAbilitys.charge1.get(p)+" §r>)> "+ "§cKöcher " +WeaponAbilitys.charge2.get(p);
							if (time == 3)
								text = "§cSammlung: " +WeaponAbilitys.charge1.get(p)+" §r>>) "+ "§cKöcher " +WeaponAbilitys.charge2.get(p);
							time++;
							
						}
						
						
						new Actionbar(text).send(p);
						// TODO Auto-generated method stub
						
					}
				}.runTaskTimer(main.plugin, 5,5);
			}
			*/
			
				ItemStack is = ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(WeaponList.weapons.get(WeaponList.SWORDNAME), 1, 0, WeaponList.SWORDNAME, null, true));
				
				
				
				new BukkitRunnable() {
					int i = 0;
					int time = 0;
					@Override
					public void run() {
						if (WeaponAbilitys.lastLaunched.containsKey(p)) {
							String spellname = WeaponAbilitys.lastLaunched.get(p).replace("spells.spells.", "");
							if (!WeaponAbilitys.cd.contains(p))
							new Actionbar("§b" + "Zauberecho" + ": "+"§c"+spellname).send(p);
						}
						if (!running) {
							this.cancel();
						}
						
					}
				}.runTaskTimer(main.plugin, 5, 5);
				p.getInventory().setItem(0, is);
			/*
			if (items.get(p).contains(removeColorTag(WeaponList.FOCUSSPHERENAME))) {
				ItemStack is = ItemStackUtils.attackSpeedify(ItemStackUtils.createItemStack(WeaponList.weapons.get(WeaponList.FOCUSSPHERENAME), 1, 0, WeaponList.FOCUSSPHERENAME, null, true));
				is = ItemStackUtils.attackDamage(is, 2);
				new BukkitRunnable() {
					int i = 0;
					int time = 0;
					@Override
					public void run() {
						if (WeaponAbilitys.lastLaunched.containsKey(p)) {
							String spellname = WeaponAbilitys.lastLaunched.get(p).replace("spells.spells.", "");
							if (!WeaponAbilitys.cd.contains(p))
							new Actionbar("§b" + removeColorTag(WeaponList.FOCUSSPHERENAME) + ": "+"§c"+spellname).send(p);
						}
						if (!running) {
							this.cancel();
						}
						
					}
				}.runTaskTimer(main.plugin, 5, 5);
				p.getInventory().setItem(0, is);
			}
			
			*/
				/*
			//Sythe
			if (items.get(p).contains(removeColorTag(WeaponList.BAMBOONAME))) {
				ItemStack is = new ItemStack(Material.BAMBOO);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("§cBambus");
				im.setUnbreakable(true);
				is.setItemMeta(im);
				// Not real Attack Damage
				is = ItemStackUtils.attackDamage(is, 2);
				p.getInventory().addItem(is);
			
				
				new BukkitRunnable() {
					int i = 0;
					int time = 0;
					@Override
					public void run() {
						if (!running) {
							this.cancel();
						}
						String text = "§aSprünge:";
						if (WeaponAbilitys.charge2.get(p) == 0) {
							text += " §7[>§7>>§7]";
						}
						if (WeaponAbilitys.charge2.get(p) == 1) {
							text += " §7[§a>§7>>§7]";
						}
						if (WeaponAbilitys.charge2.get(p) == 2) {
							text += " §7[§a>>§7>§7]";
						}
						if (WeaponAbilitys.charge2.get(p) == 3) {
							text += " §7[§a>>>§7]";
						}
						if (WeaponAbilitys.pList.containsKey(p)) {
							for (Player pl : WeaponAbilitys.pList.get(p)) {
								ParUtils.createRedstoneParticle(pl.getEyeLocation(),0.1F, 0.1F, 0.1F, 3, Color.LIME, 1, p);
							}
						}
						
						new Actionbar(text).send(p);
						
					}
				}.runTaskTimer(main.plugin, 5,5);
			}
			*/
		}
		
		//SYTHE
		
		
	}
	
	public static void stopLoop() {
		running = false;
	}

}
