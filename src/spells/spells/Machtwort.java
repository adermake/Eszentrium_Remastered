package spells.spells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import esze.utils.NBTUtils;
import esze.utils.ParUtils;
import net.minecraft.server.v1_16_R3.Particles;
import spells.spellcore.Cooldowns;
import spells.spellcore.Spell;
import spells.spellcore.SpellType;
import spells.stagespells.Repulsion;

public class Machtwort extends Spell {

	
	public Machtwort() {
		name = "§3Machtwort";
		multihit = true;
		canHitSelf = true;
		hitboxSize = 4;
		steprange = 20 *  10;
		cooldown = 20 * 70;
		
		setLore("Beschwört einen Zauberkreis, in dem der Cooldown aller Zauber stark reduziert wird.");
		setBetterLore("Beschwört einen Zauberkreis, in dem der Cooldown aller Zauber stark reduziert wird. Gegner werden außerdem aus dem Kreis geworfen.");
		
	
		addSpellType(SpellType.AURA);
		addSpellType(SpellType.SUPPORT);
	}
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		loc = caster.getLocation().add(0,-0.5,0);
		playSound(Sound.ITEM_TOTEM_USE,loc,1,2);
		new Repulsion(4, 2, caster, caster.getLocation(), name);
		
		if (refined)
			steprange = 20 * 20;
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if (step % 60 == 0 && refined) {
			new Repulsion(4, 2, caster, loc, name);
			ParUtils.parKreisDot(Particles.TOTEM_OF_UNDYING, loc,1, 1, 1, new Vector(0,1,0));
		}
	}

	@Override
	public void display() {
		if (step % 5 == 0) {
			playSound(Sound.BLOCK_BEACON_POWER_SELECT,loc,0.3F,1);
		}
		// TODO Auto-generated method stub
		if (step % 3 == 0)
		ParUtils.parKreisRedstone(Color.LIME, 1, loc, 4, 0, 1, 20, new Vector(0,1,0));
		
		for (int i = 0;i<2;i++) {
			Location l = ParUtils.stepCalcCircle(loc, 4, new Vector(0,1,0), 0, step +22*i);
			ParUtils.createFlyingParticle(Particles.LARGE_SMOKE, l, 0, 0, 0, 1, 0, new Vector(0,1,0));
			ParUtils.createFlyingParticle(Particles.TOTEM_OF_UNDYING, l, 0, 0, 0, 1, 1, new Vector(0,0.7F,0));

			ParUtils.createRedstoneParticle(l, 0.3F, 0.3F, 0.3F, 4, Color.GREEN, 1);
		}
		
	}

	@Override
	public void onPlayerHit(Player p) {
		//p.damage(0.5F);
		ParUtils.createParticle(Particles.ENCHANT, p.getLocation().add(0,-1,0), 1, 0, 1, 8, 1);
		// TODO Auto-generated method stub
		tickCooldown(15,p);
		
	}

	public void tickCooldown(int downticker,Player p)
	{
		int barcount = 10;
			
			for (int slot = 0;slot<p.getInventory().getSize();slot++) {
				ItemStack i = p.getInventory().getItem(slot);
				
				
				if (i ==  null ) {
					continue;
				}
				if (i.getItemMeta().getDisplayName().contains("Machtwort")) {
					continue;
				}
				String nbtData = NBTUtils.getNBT("Cooldown", i);
				String burn = NBTUtils.getNBT("Burn", i);
				if (burn == "true") {
					
					i = new ItemStack(Material.BOOK);
					ItemMeta m = i.getItemMeta();
					m.setDisplayName("§7Verbranntes Buch");
					
					i.setItemMeta(m);
					p.getInventory().setItem(slot, i);
					return;
				}
				if (!(nbtData.equals(""))) {
					double cooldown = Double.parseDouble(nbtData);
					cooldown -= downticker;
					if (cooldown<=0) {
						
						ItemMeta im = i.getItemMeta();
						im.setDisplayName(NBTUtils.getNBT("OriginalName", i));
						i.setItemMeta(im);
						i.setType(Material.ENCHANTED_BOOK);
						i = NBTUtils.setNBT("Cooldown", "0", i);
						p.getInventory().setItem(slot, i);
					}
					else {
						
						double maxCd = Double.parseDouble(NBTUtils.getNBT("MaxCooldown", i));
						ItemMeta im = i.getItemMeta();
						String currentName = NBTUtils.getNBT("OriginalName", i);
						double per = cooldown/maxCd;
						currentName = currentName + " §7<[";
						int anticount = 0;
						for (int count = 0;count<= (int)(per*barcount);count++) {
							currentName = currentName + "§4|";
							anticount++;
						}
						anticount = barcount-anticount;
						for (int count = 0;count<= anticount;count++) {
							currentName = currentName + "§8|";
						}
						currentName = currentName + "§7]>";
						im.setDisplayName(currentName);
						i.setItemMeta(im);
						i.setType(Material.BOOK);
						i = NBTUtils.setNBT("Cooldown", ""+cooldown, i);
						p.getInventory().setItem(slot, i);
						
					}
				}
				
			
			
		}
	}
	public void onEntityHit(LivingEntity ent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpellHit(Spell spell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockHit(Block block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
