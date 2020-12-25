package spells.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import spells.spellcore.Spell;

public class Crossbow extends Spell {
	ArmorStand a = null;
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
		a = createArmorStand(caster.getLocation().setDirection(new Vector(1,0,0)));
		
		
		//a.setVisible(true);
		a.setArms(true);
	
		
	}

	@Override
	public void cast() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}
	int delay = 0;
	@Override
	public void move() {
		// TODO Auto-generated method stub
		Vector v = caster.getLocation().toVector().subtract(a.getLocation().toVector());
		Location l1 = caster.getLocation();
		l1.setDirection(v);
		a.setRightArmPose(new EulerAngle(Math.PI/2-Math.PI*2*l1.getPitch()/360,-Math.toRadians(80)+Math.PI*2*l1.getYaw()/360, 0));
		delay++;
		if (delay > 5) {
			delay = 0;
			Arrow ar = (Arrow) spawnEntity(EntityType.ARROW,a.getLocation().add(v.normalize()).add(new Vector(0,1,0)));
					ar.setVelocity(v.multiply(3));
			playSound(Sound.ENTITY_ARROW_SHOOT, loc, 1, 1);
			setCrossbow(false);
		}
		if (delay == 2) {
			setCrossbow(true);
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerHit(Player p) {
		// TODO Auto-generated method stub
		
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
		
	}

	
	public void setCrossbow(boolean on) {
		if (on) {
			ItemStack is = new ItemStack(Material.CROSSBOW);
			CrossbowMeta cm = (CrossbowMeta) is.getItemMeta();
			ArrayList<ItemStack> ar = new ArrayList<ItemStack>();
			ar.add(new ItemStack(Material.ARROW));
		
			cm.setChargedProjectiles(ar);
			is.setItemMeta(cm);
			a.getEquipment().setItemInMainHand(is);
		}
		else {
			ItemStack is = new ItemStack(Material.CROSSBOW);
			a.getEquipment().setItemInMainHand(is);
		}
	}
}
