package spells.stagespells;

import org.bukkit.util.Vector;

public class ChainSegment {
  public Vector loc;
  
  public Vector lastLoc;
  
  boolean locked = false;
  
  public ChainSegment(Vector loc) {
    this.loc = loc.clone();
    this.lastLoc = loc.clone();
  }
  
  public boolean isLocked() {
    return this.locked;
  }
  
  public void setLocked(boolean locked) {
    this.locked = locked;
  }
}
