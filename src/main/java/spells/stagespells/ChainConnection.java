package spells.stagespells;

import org.bukkit.entity.ArmorStand;

public class ChainConnection {

	public ChainSegment segmentA;
	public ChainSegment segmentB;
	public ArmorStand chain;
	public float length;
	
	
	public ChainConnection(ChainSegment segmentA,ChainSegment segmentB,float length,ArmorStand c) {
		this.segmentA = segmentA;
		this.segmentB = segmentB;
		this.length = length;
		chain = c;
	}
	
}
