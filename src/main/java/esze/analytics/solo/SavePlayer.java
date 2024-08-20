package esze.analytics.solo;

import java.util.ArrayList;

public class SavePlayer {
	
	private String name;
	private SaveSelection s1;
	private SaveSelection s2;
	private SaveSelection s3;
	private SaveSelection s4;
	private String d1;
	private String d2;
	private String d3;
	private String d4;
	private boolean dead = false;
	private boolean win = false;
	
	public SavePlayer() {
		d1 = "";
		d2 = "";
		d3 = "";
		d4 = "";
		dead = false;
	}

	public SavePlayer(String s) {
		String[] list = OldSaveUtils.readString(s);
		name = list[0];
		int i = 1;
		s1 = (list.length > i) ? new SaveSelection(list[i]) : null;
		i++;
		d1 = (list.length > i) ? list[i] : null;
		i++;
		s2 = (list.length > i) ? new SaveSelection(list[i]) : null;
		i++;
		d2 = (list.length > i) ? list[i] : null;
		i++;
		s3 = (list.length > i) ? new SaveSelection(list[i]) : null;
		i++;
		d3 = (list.length > i) ? list[i] : null;
		i++;
		s4 = (list.length > i) ? new SaveSelection(list[i]) : null;
		i++;
		d4 = (list.length > i) ? list[i] : null;
		if (d4 != null) {
			if (!d4.equals("")) {
				dead = true;
			}
		} else {
			win = true;
		}
	}
	
	@Override
	public String toString() {
		String s = "[";
		s += name;
		
		s += (s1 != null) ? ("," + s1.toString()) : ("");
		s += (d1 != null) ? ("," + d1) : ("");
		s += (s2 != null) ? ("," + s2.toString()) : ("");
		s += (d2 != null) ? ("," + d2) : ("");
		s += (s3 != null) ? ("," + s3.toString()) : ("");
		s += (d3 != null) ? ("," + d3) : ("");
		s += (s4 != null) ? ("," + s4.toString()) : ("");
		s += (d4 != null) ? ("," + d4) : ("");
		
		return s + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SaveSelection getS1() {
		return s1;
	}

	public void setS1(SaveSelection s1) {
		this.s1 = s1;
	}

	public SaveSelection getS2() {
		return s2;
	}

	public void setS2(SaveSelection s2) {
		this.s2 = s2;
	}

	public SaveSelection getS3() {
		return s3;
	}

	public void setS3(SaveSelection s3) {
		this.s3 = s3;
	}

	public SaveSelection getS4() {
		return s4;
	}

	public void setS4(SaveSelection s4) {
		this.s4 = s4;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = OldSaveUtils.rmColor(d1);
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = OldSaveUtils.rmColor(d2);
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = OldSaveUtils.rmColor(d3);
	}

	public String getD4() {
		return d4;
	}

	public void setD4(String d4) {
		this.d4 = OldSaveUtils.rmColor(d4);
	}

	public boolean addDeath(String sele) {
		if (d1 == null || d1.equals("")) {d1 = OldSaveUtils.rmColor(sele);return false;}
		if (d2 == null || d2.equals("")) {d2 = OldSaveUtils.rmColor(sele);return false;}
		if (d3 == null || d3.equals("")) {d3 = OldSaveUtils.rmColor(sele);return false;}
		if (d4 == null || d4.equals("")) {d4 = OldSaveUtils.rmColor(sele);return true;}
		dead = true;
		return true;
	}

	public void addSelect(SaveSelection sele) {
		if (s1 == null) {s1 = sele;return;}
		if (s2 == null) {s2 = sele;return;}
		if (s3 == null) {s3 = sele;return;}
		if (s4 == null) {s4 = sele;return;}
	}
	
	public ArrayList<SaveSelection> getSelections() {
		ArrayList<SaveSelection> selections = new ArrayList<>();
		if (s1 != null) {selections.add(s1);}
		if (s2 != null) {selections.add(s2);}
		if (s3 != null) {selections.add(s3);}
		if (s4 != null) {selections.add(s4);}
		return selections;
	}
	
	public ArrayList<SaveSelection> getNormalSelections() {
		ArrayList<SaveSelection> selections = new ArrayList<>();
		if (s1 != null) {selections.add(s1);}
		if (s2 != null) {selections.add(s2);}
		if (s3 != null) {selections.add(s3);}
		return selections;
	}
	
	public ArrayList<String> getDeaths() {
		ArrayList<String> deaths = new ArrayList<>();
		if (d1 !=null && !d1.equals("")) {deaths.add(d1);}
		if (d2 !=null && !d2.equals("")) {deaths.add(d2);}
		if (d3 !=null && !d3.equals("")) {deaths.add(d3);}
		if (d4 !=null && !d4.equals("")) {deaths.add(d4);}
		return deaths;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setWinner() {
		win = true;
	}
	
	public boolean isWinner() {
		return win;
	}
	
	public int getDeathCount() {
		return getDeaths().size();
	}

}
