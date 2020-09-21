package esze.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_15_R1.EntityFishingHook;
import net.minecraft.server.v1_15_R1.EntityHuman;


public class FishingLine extends EntityFishingHook {
    private Item item;
 
    public FishingLine(EntityHuman player, Item item){
    	
    	super( player,player.getWorld(),1,1);
       //super(((CraftWorld) player.getWorld()).getHandle(), ((CraftPlayer) player).getHandle(), 0, 0);
       
        this.item = item;
    }
 /*
    @Override
    public void l(){
        if(item != null){
            ItemStack hand = this.owner.bx();
            boolean shouldRemove = false;
 
            if(this.owner.dead || !(this.owner.isAlive())){
                shouldRemove = true;
            }
 
            if(hand == null){
                shouldRemove = true;
            } else {
                if(hand.getItem() != item){
                    shouldRemove = true;
                }
            }
 
            if(this.e(this.owner) > 1024.0D){
                shouldRemove = true;
            }
 
            if(shouldRemove){
                super.die();
                super.owner.hookedFish = null;
            }
        }
    }
   */
    @Override
    public void l(float f) {
    	// TODO Auto-generated method stub
    	
    }
    public void spawn(Location loc){
        net.minecraft.server.v1_15_R1.World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
 
        nmsWorld.addEntity(this);
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
    }
}