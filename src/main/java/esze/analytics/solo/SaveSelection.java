package esze.analytics.solo;

import java.util.ArrayList;
import java.util.regex.Pattern;

import esze.main.main;

public class SaveSelection {
	
	private String sp1;
	private String sp2;
	private String sp3;
	private String sp4;
	private String sp5;
	private String chsp;
	
	public SaveSelection() {}
	
	public SaveSelection(String chsp ,String sp1, String sp2, String sp3, String sp4, String sp5) {
		this.sp1 = OldSaveUtils.rmColor(sp1);
		this.sp2 = OldSaveUtils.rmColor(sp2);
		this.sp3 = OldSaveUtils.rmColor(sp3);
		this.sp4 = OldSaveUtils.rmColor(sp4);
		this.sp5 = OldSaveUtils.rmColor(sp5);
		this.chsp = OldSaveUtils.rmColor(chsp);
	}



	public SaveSelection(String s) {
		s = s.replaceAll(Pattern.quote("["), "");//i
		s = s.replaceAll(Pattern.quote("]"), "");
		String[] g = s.split(",");
		if (g.length < 1) {return;}
		chsp = g[0];
		if (g.length < 2) {return;}
		sp1 = g[1];
		if (g.length < 3) {return;}
		sp2 = g[2];
		if (g.length < 4) {return;}
		sp3 = g[3];
		if (g.length < 5) {return;}
		sp4 = g[4];
		if (g.length < 6) {return;}
		sp5 = g[5];
	}
	
	@Override
	public String toString() {
		String s = "[";
		
		s += (chsp == null ? "" : chsp) + ",";
		s += (sp1 == null ? "" : sp1) + ",";
		s += (sp2 == null ? "" : sp2) + ",";
		s += (sp3 == null ? "" : sp3) + ",";
		s += (sp4 == null ? "" : sp4);
		s += (sp5 == null ? "" : sp5);
		
		return s + "]";
	}
	
	public String getSp1() {
		return sp1;
	}
	public void setSp1(String sp1) {
		this.sp1 = OldSaveUtils.rmColor(sp1);
	}
	public String getSp2() {
		return sp2;
	}
	public void setSp2(String sp2) {
		this.sp2 = OldSaveUtils.rmColor(sp2);
	}
	
	public String getSp3() {
		return sp3;
	}
	public void setSp3(String sp3) {
		this.sp3 = OldSaveUtils.rmColor(sp3);
	}
	public String getSp4() {
		return sp4;
	}
	public void setSp4(String sp4) {
		this.sp4 = OldSaveUtils.rmColor(sp4);
	}
	
	public String getSp5() {
		return sp5;
	}
	public void setSp5(String sp5) {
		this.sp5 = OldSaveUtils.rmColor(sp5);
	}
	
	public String getChsp() {
		return chsp;
	}
	public void setChsp(String chsp) {
		this.chsp = OldSaveUtils.rmColor(chsp);
	}
	
	
	public ArrayList<String> getChoices() {
		ArrayList<String> choices = new ArrayList<>();
		choices.add(sp1);
		choices.add(sp2);
		choices.add(sp3);
		choices.add(sp4);
		choices.add(sp5);
		return choices;
	}

	
}
