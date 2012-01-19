package Kalender;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Datum extends GregorianCalendar{
	/**
	 * Kalender:
	 * Monate gehen von 1-12, statt von 0-11, wei bei der Klassse GregorianCalender.
	 */
public Datum(){
		super();
}	
public void setYear(int y){
	this.set(Calendar.YEAR, y);
	}
public void setHours(int h){
	this.set(Calendar.HOUR_OF_DAY, h);	
	}
public void setMinutes(int m){
	this.set(Calendar.MINUTE, m);
}
public void setSeconds(int s){
	this.set(Calendar.SECOND, s);
}
public void setMonth(int m){
	this.set(Calendar.MONTH, m-1);
}	
public void setDayOfMonth(int d){
	this.set(Calendar.DAY_OF_MONTH, d);
}
public int getYear(){
	return this.get(Calendar.YEAR);
}
public int getHours(){
	return this.get(Calendar.HOUR_OF_DAY);	
}
public int getMinutes(){
	return this.get(Calendar.MINUTE);
}
public int getSeconds(){
	return this.get(Calendar.SECOND);
}
public int getMonth(){
	return this.get(Calendar.MONTH)+1;
}	
public int getDayOfMonth(){
	return this.get(Calendar.DAY_OF_MONTH);
}

/**
 * 
 * @return die Kalenderwoche 01.01.2011 - KW 01
 */
public int getWeek(){
	/*
	int week = 1;
	Date d2 = new Date();
	d2.setYear(d.getYear());
	d2.setMonth(1);
	d2.setDate(1);
	while(d2.before(d)){
		week++;
		d2.setDate(d2.getDate()+7);
	}
	*/
	return this.get(WEEK_OF_YEAR);
}

/**
 * Hilfsfunktion um ein Datum schneller zu befüllen.
 * @param d Das Datum aus dem die Daten bezogen werden.
 * Befüllt:
 * den Tag
 * Den Monat
 * Das Jahr
 */
public void setDate(Datum d){
	setYear(d.getYear());
	setMonth(d.getMonth());
	setDayOfMonth(d.getDayOfMonth());
}

/**
 * Hilfsfunktion um ein Datum schneller zu befüllen.
 * @param d Das Datum aus dem die Zeit bezogen wird.
 * Befüllt:
 * Stunde
 * Minute
 * Sekunde
 */
public void setTime(Datum d){
	setHours(d.getHours());
	setMinutes(d.getMinutes());
	setSeconds(d.getSeconds());
}

@Override
public String toString(){
	return getDayOfMonth()+"."+getMonth()+"."+getYear()+" "+getHours()+":"+getMinutes();
}


/**
 * kopiert werden Jahr, Monat, Tag, Stunde, Minute und Sekunde.
 */
@Override
public Datum clone(){
	Datum ret = new Datum();//this.clone();
	ret.setHours(getHours());
	ret.setMinutes(getMinutes());
	ret.setSeconds(getSeconds());
	ret.setYear(getYear());
	ret.setMonth(getMonth());
	ret.setDayOfMonth(getDayOfMonth());
	return ret;
}

}
