package Kalender.HSRottenburgKalender;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import form.Form;

import HTTP.HTTP;
import Kalender.Datum;
import Kalender.Kalender;
import Kalender.HSRottenburgKalender.HSRottenburgVorlesungsTermin;
import Kalender.VorlesungsTermin;
import Kalender.Exception.NichtAngemeldetException;


public class HSRottenburgKalender implements Kalender {
	
	public static final String HSROTTENBURG_KALENDER_URL = "http://www.fh-rottenburg.de/studium/stundenplan/html-untis/";//51/c/c00011.htm
	public static final String HSROTTENBURG_KALENDER_POST_CONTENT = "";
	public static final String HSROTTENBURG_KALENDER_REGEXP = "<div class=\"tbMonthDay\"[^<]+<[^<]+<div class=\"tbsubhead\">[^<]+<a title=\"([^\"]+)\"[^<]+</a>[^<]+</div>[^<]+(<div class=\"appMonth\">[^<]+<a title=\"[^\"]+\"[^<]+<[^<]+<[^<]+</div>[^<]*)+";

	public HSRottenburgKalender(){
		

	}
	
	public boolean anmelden(String username, String passwort){
		return true; //kein anmelden erforderlich!
	}
	
	@Override
	public ArrayList<VorlesungsTermin> getVorlesungstermine(Datum von, Datum bis){
		ArrayList<VorlesungsTermin> vorlesungen = new ArrayList<VorlesungsTermin>();
		int weekv=von.getWeek();
		int weekb=bis.getWeek();
		while(weekv<=weekb){
			ArrayList<HSRottenburgVorlesungsTermin> ret = getCalenderWeek(weekv, 11);
			for(HSRottenburgVorlesungsTermin vt : ret)vorlesungen.add(vt);
			weekv++;
		}
		return vorlesungen;
	}

	public String convert(int number, int digit) {
	    String buffer = String.valueOf(number);
	    while(buffer.length() != digit)
	        buffer="0" + buffer;
	    return buffer;
	}
	
	/**
	 * @return Alle Vorlesungen der aktuellen Kalenderwoche für die in semesterNr übergebenen Kurs.
	 */
	private ArrayList<HSRottenburgVorlesungsTermin> getCalenderWeek(int kalenderwoche, int semesterNr){
		String url = HSROTTENBURG_KALENDER_URL+String.valueOf(kalenderwoche)+"/c/c"+convert(semesterNr,5)+".htm";
		
		ArrayList<HSRottenburgVorlesungsTermin> vorlesungen = new ArrayList<HSRottenburgVorlesungsTermin>();
		ArrayList<Datum> datums = new ArrayList<Datum>(); //hier werden die Datumsangaben gespeichert
		WebClient client = new WebClient();
		//ArrayList<HashMap<String,String>> tage = new ArrayList<HashMap<String,String>>();
		int jahr;
		try {
			HtmlPage page = client.getPage(url);//z.B. "http://www.fh-rottenburg.de/studium/stundenplan/html-untis/51/c/c00011.htm"
			HtmlTable table = (HtmlTable) page.getByXPath("//table[@border='3']").get(0);
			if(table==null || !(table.getRowCount()>0))throw new Exception("Fehler beim Laden der Tabelle");
			int aktuelleZeile[] = new int[table.getRowCount()]; //speichert, 
			for(int i=0;i<table.getRowCount();i++) aktuelleZeile[i]=0;
			
			//hier wird das Jahr der Tabelle ausgelesen:
			String regexp="<font size=\"5\" face=\"Arial\">[^\\d]*\\d?\\d/(\\d{4})";
			Pattern p = Pattern.compile (regexp, Pattern.CASE_INSENSITIVE);
		    Matcher m = p.matcher (page.asXml());
		    if(m.find()){
		    	jahr = Integer.parseInt(m.group(1));
		    }else{
		    	throw new Exception("Fehler beim Auslesen des Jahres");
		    }
		    //Ende! Jahr auslesen
for(int reihenNr=0;reihenNr<table.getRowCount();reihenNr++){
	//Dieser Bereich wird auch für leere Zeilen ausgeführt, also jede Zeile=========================
	HtmlTableRow row = table.getRow(reihenNr);
	System.out.println("\n\nReihe "+reihenNr+" : ");
	//System.out.println(row.asText());
	for(HSRottenburgVorlesungsTermin termin:vorlesungen)termin.setDauer(termin.getDauer()-1); //Die Dauer aller Vorlesungstermine reduzieren
	//===============================================================================================
	if(!row.getCells().isEmpty()){ //hin und wieder kommen Zeilen die leer sind
		//dieser Bereich wird für alle nicht leeren Zeilen ausgeführt (also die im Browser sichtbaren)===
		if(reihenNr==0){//wenn es die erste Reihe ist: (in ihr stehen die Vorlesungstage dieser Woche)
			Iterator<HtmlTableCell> zellen=row.getCellIterator();
			zellen.next();//wir überspringen die erste Zelle, da diese leer ist
			while(zellen.hasNext()){
				HtmlTableCell z = zellen.next();
				regexp = "(\\d\\d)\\.(\\d\\d)\\.";
				p = Pattern.compile (regexp, Pattern.CASE_INSENSITIVE);
			    m = p.matcher (z.asText()); // z.asText() == z.B. Montag 01.03.
			    if(m.find()){
			    	Datum d = new Datum();
			    	d.setDayOfMonth(Integer.parseInt(m.group(1)));
			    	d.setMonth(Integer.parseInt(m.group(2)));
			    	d.setYear(jahr);
			    	datums.add(d); //es werden die Datumsangaben in das Array gelegt. Feld 0 steht also für Spalte 1!
			    }else{
			    	throw new Exception("Fehler beim Auslesen des Datums");
			    }
			    
			}
		}else{//wenn es nicht die erste (reihenNr==0) Reihe ist:
			Datum vonZeit= new Datum();
			Datum bisZeit= new Datum();
			vonZeit.setSeconds(0);
			bisZeit.setSeconds(0);
			vonZeit.setMinutes(0);
			bisZeit.setMinutes(0);
			vonZeit.setHours(0);
			bisZeit.setHours(0);
			HtmlTableCell cell = row.getCells().get(0); // in der ersten Zelle steht die Vorlesungsstunde und deren Zeit
			HtmlTable t = (HtmlTable) cell.getElementsByTagName("table").item(0);
			//ab hier wird dann die Tabelle (t) verarbeitet, in welcher die Stunde und dauer derselbigen gespeichert ist
			//diese sieht folgendermaßen aus:
			// Zeile 1: 	Stunde 	VonZeit
			// Zeile 2: 	null 	BisZeit
			try{ //hier kommt ein Try Block
				vonZeit.setHours(Integer.parseInt(t.getRow(0).getCell(1).asText().split(":")[0]));
				vonZeit.setMinutes(Integer.parseInt(t.getRow(0).getCell(1).asText().split(":")[1]));
				bisZeit.setHours(Integer.parseInt(t.getRow(1).getCell(0).asText().split(":")[0]));
				bisZeit.setMinutes(Integer.parseInt(t.getRow(1).getCell(0).asText().split(":")[1]));
			}catch(Exception e){ // läuft irgendetwas schief, dann ist die Tabelle falsch formatiert oder falsch geladen.
				e.printStackTrace();
				throw new Exception("Fehler beim Auslesen der Tabelle.\n Konnte Zeit nicht auslesen.");
			}
			
			//hier werden die Bis-Zeiten gesetzt:
			for(HSRottenburgVorlesungsTermin termin:vorlesungen){
				if(termin.getDauer()==0){
					Datum end = new Datum();
					end.setDate(termin.start);//hier kann das Datum des Startwerts genommen werden, weil eine Vorlesung immer an einem Tag stattfindet.
					end.setTime(bisZeit);
					termin.end = end.clone();
					
				}
			}
	
			Iterator<HtmlTableCell> zellen=row.getCellIterator();
			zellen.next();//wir überspringen die erste Zelle, da diese gerade eben ausgewertet wurde
			int aktuelleSpalte=0;
			while(zellen.hasNext()){ //hier wird über alle Spalten in der Tabelle iteriert. 
			// es kann sein, dass wenn ein element den rowspan 2 hat, dann die Reihe darunter an dieser Stelle keine Zelle hat. Dies Prüfen und Berücksichtigen.
				//denn mit zellen.next() käme man dann eine Spalte zu weit.
			if(aktuelleZeile[aktuelleSpalte]<reihenNr){
				HtmlTableCell zelle = zellen.next(); // diese Zelle
				if(zelle.getElementsByTagName("table").isEmpty())throw new Exception("Fehler beim Einlesen der Tabelle. Zelle enthält keine Tabelle.");
				HtmlTable vorlesungsBeschreibung = (HtmlTable) zelle.getElementsByTagName("table").get(0);//es gibt nur eine Tabelle, deshalb muss es die erste sein.
				//in der Vorlesungsbeschreibung stehen nun alle Informationen zur Vorlesungsstunde. Ist diese Tabelle leer, dann findet auch keine Vorlesung statt
				if(vorlesungsBeschreibung.asText().length()>0){
				//Vorlesung erstellen:
				HSRottenburgVorlesungsTermin vorlesung = new HSRottenburgVorlesungsTermin();
				vorlesung.description=vorlesungsBeschreibung.asText();
				vorlesung.location = "";
				Datum start = new Datum();
				start.setDate(datums.get(aktuelleSpalte));
				start.setTime(vonZeit);
				vorlesung.start = start.clone();
				vorlesungen.add(vorlesung); //und abspeichern
				//hier ist aber noch kein bis_Zeitpunkt gesetzt
				vorlesung.setDauer(zelle.getRowSpan()-2); //ziemlicher murks!!
				//ENDE! Vorlesung erstellen und abspeichern
				}
				aktuelleZeile[aktuelleSpalte]+=1;
				if(zelle.getRowSpan()>1)aktuelleZeile[aktuelleSpalte]+=zelle.getRowSpan()-1;
				aktuelleSpalte++;
			}else{
				aktuelleSpalte++;
			}
			}
		}
	}
	}
	//System.out.println(table.asText());
	} catch (Exception e) {
	e.printStackTrace();
	}
	//System.out.println("VORLESUNGEN: ");
	//for(final HSRottenburgVorlesungsTermin v : vorlesungen)System.out.println(v);
	
	return vorlesungen;
	    

	}

	@Override
	public boolean anmelden(Form formular) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Form getFormular() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
