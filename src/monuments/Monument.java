package monuments;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import spells.spellcore.Spell;

public abstract class Monument extends Spell {
	
	public static ArrayList<Monument> monuments = new ArrayList<Monument>();
	
	ArrayList<Player> inside = new ArrayList<Player>();
	ArrayList<ArmorStand> cores = new ArrayList<ArmorStand>();
	HashMap<ArmorStand,Vector> blocks = new HashMap<ArmorStand,Vector>();
	public double range = 30;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		hitSpell = false;
		
		multihit = true;
		monuments.add(this);
		onConstruct();
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
		hitboxSize = range;
		
		for (Player p : inside) {
			if (p.getLocation().distance(loc)>range) {
				onLeave(p);
				inside.remove(p);
			}
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		if (!inside.contains(p)) {
			inside.add(p);
		}
	}

	@Override
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
		monuments.remove(this);
	}
	
	float armorStandHeadConstant = 0.8F;
	
	public void addBlock(Material m,Vector gridPos) {
		ArmorStand ar = createArmorStand(loc);
		ar.getEquipment().setHelmet(new ItemStack(m));
		Vector v = new Vector(gridPos.getX()*armorStandHeadConstant,gridPos.getY()*armorStandHeadConstant,gridPos.getZ()*armorStandHeadConstant);
		blocks.put(ar,v);
	}
	public void addCore(Material m,Vector gridPos) {
		ArmorStand ar = createArmorStand(loc);
		ar.getEquipment().setHelmet(new ItemStack(m));
		Vector v = new Vector(gridPos.getX()*armorStandHeadConstant,gridPos.getY()*armorStandHeadConstant,gridPos.getZ()*armorStandHeadConstant);
		cores.add(ar);
		blocks.put(ar,v);
	}
	
	public abstract void onEnter(Player p);
	public abstract void onLeave(Player p);
	public abstract void onEnterEnemy(Player p);
	public abstract void onLeaveEnemy(Player p);
	public abstract void onActivate(Player p);
	public abstract void onConstruct();

}
