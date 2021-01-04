package esze.analytics.solo;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveGame {
	
	private HashMap<SavePlayer, Integer> map = new HashMap<>();
	
	public SaveGame() {
		
	}
	
	public SaveGame(String s) {
		String[] args = OldSaveUtils.readString(s);
		for (int i = 0; (i+1) < args.length; i+=2) {
			if (args[i+1].equals("")) {
				map.put(new SavePlayer(args[i]), args.length/2);
			} else {
				SavePlayer p = new SavePlayer(args[i]);
				map.put(p, Integer.parseInt(args[i+1]));
				
				if (Integer.parseInt(args[i+1]) == 0) {
					p.isWinner();
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "[";
		
		for (SavePlayer sp : map.keySet()) {
			s += sp.toString() + ",";
			s += map.get(sp).intValue() + ",";
		}
		if (s.length() > 1) {
			s = s.substring(0, s.length()-1);
		}
		return s + "]";
	}

	public HashMap<SavePlayer, Integer> getMap() {
		return map;
	}

	public void addPlayer(String s) {
		for (SavePlayer p : map.keySet()) {
			if (p.getName().equals(s)) {
				return;
			}
		}
		SavePlayer sav = new SavePlayer();
		sav.setName(s);
		map.put(sav, 0);
	}

	public void addDeath(String name, String cause) {
		boolean fin = false;
		SavePlayer pl = null;
		for (SavePlayer p : map.keySet()) {
			if (p.getName().equals(name) && !p.isDead()) {
				fin = p.addDeath(cause);
				pl = p;
			}
		}
		
		if (fin) {
			int max = map.size()-1;
			
			while (map.values().contains(max) && max > 0) {
				max -= 1;
			}
			
			if (pl != null) {
				map.remove(pl);
				map.put(pl, max);
			}
		}
	}
	
	public void setWinner(String name) {
		HashMap<SavePlayer, Integer> over = new HashMap<>();
		for (SavePlayer p : map.keySet()) {
			if (p.getName().equals(name)) {
				over.put(p, 0);
			} else if (map.get(p) == 0) {
				int max = map.size()-1;
				
				while ((map.values().contains(max) || over.values().contains(max)) && max > 0) {
					max -= 1;
				}
				if (max == 0) {
					max = 1;
				}
				over.put(p, max);
			} else {
				over.put(p, map.get(p));
			}
		}
		map = over;
	}
	
	public void endGame() {
		ArrayList<SavePlayer> sp = new ArrayList<SavePlayer>(); 
		for (SavePlayer p : map.keySet()) {
			if (p == null && !p.isDead() && !p.isWinner()) {
				sp.add(p);
			}
		}
		for (SavePlayer p : sp) {
			map.remove(p);
		}
		
	}

	public void addSelect(String name, SaveSelection sele) {
		for (SavePlayer p : map.keySet()) {
			if (p.getName().equals(name) && !p.isDead()) {
				p.addSelect(sele);
			}
		}
	}
	

}
