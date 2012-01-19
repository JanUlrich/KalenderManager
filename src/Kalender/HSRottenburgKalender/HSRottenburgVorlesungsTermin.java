package Kalender.HSRottenburgKalender;

import Kalender.VorlesungsTermin;

public class HSRottenburgVorlesungsTermin extends VorlesungsTermin {
private int dauer;

public HSRottenburgVorlesungsTermin(){
	super();
	dauer=0;
}

	public void setDauer(int schulstunden){
		dauer=schulstunden;
	}
	
	public int getDauer(){
		return dauer;
	}
	
}
