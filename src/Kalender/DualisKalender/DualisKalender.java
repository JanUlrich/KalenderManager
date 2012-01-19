package Kalender.DualisKalender;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import form.Form;
import form.TextInput;

import HTTP.HTTP;
import Kalender.Datum;
import Kalender.Kalender;
import Kalender.VorlesungsTermin;
import Kalender.Exception.NichtAngemeldetException;


public class DualisKalender implements Kalender {
	
	public static final String DUALIS_URL = "https://dualis.dhbw.de/";
	public static final String DUALIS_LOGIN_URL = DUALIS_URL+"scripts/mgrqcgi";
	public static final String DUALIS_COOKIE = "cnsc="+generateCookie();
	public static final String DUALIS_ARGUMENTE_REGEXP = "ARGUMENTS=([^,]+),";
	public static final String DUALIS_KALENDER_URL = DUALIS_LOGIN_URL+"?APPNAME=CampusNet&PRGNAME=MONTH&ARGUMENTS=";
	public static final String DUALIS_KALENDER_MONATSANSICHT_ARGUMENTS_SUFFIX = ",-N000031,-A";//",-A,-A,-N1";
	public static final String DUALIS_HEADER_LOC_NAME ="location";
//"<div class=\"tbMonthDay\"[^<]+<[^<]+<div class=\"tbsubhead\">[^<]+<a title=\"([^\"]+)\"[^<]+</a>[^<]+</div>[^<]+((<div class=\"appMonth\">[^<]+<a title=\"[^\"]+\"[^<]+<[^<]+<[^<]+</div>[^<]*)+)"
	//Info zu DUALIS_KALENDER_REGEXP: In der ersten Klammer steht der Tag und in der zweiten Klammer ( Uhrzeit / Ort / Vorlesungsfach )
	public static final String DUALIS_KALENDER_REGEXP = "<div class=\"tbMonthDay\"[^<]+<[^<]+<div class=\"tbsubhead\">[^<]+<a title=\"([^\"]+)\"[^<]+</a>[^<]+</div>[^<]+(<div class=\"appMonth\">[^<]+<a title=\"[^\"]+\"[^<]+<[^<]+<[^<]+</div>[^<]*)+";
	//"<div class=\"tbMonthDay\"[^<]+<div class=\"tbsubhead\">[^<]+<a title=\"([^\"]+)\"[^<]+</a>[^<]+</div>[^<]+<div class=\"appMonth\">[^<]+<a title=\"([^\"]+)\"";
	
	private String args;
	

	public ArrayList<VorlesungsTermin> getVorlesungstermine(Datum von, Datum bis){
		String month = getCalenderMonth(args);
		Pattern p = Pattern.compile (DUALIS_KALENDER_REGEXP, Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher (month);
	    
	    while(m.find()){
	    	System.out.println("Vorlesung am " + m.group(1) + " -> " + m.group(2));
	    	for(int i=1;i<=m.groupCount();i++)System.out.println(m.group(i));
	    }
		return new ArrayList<VorlesungsTermin>();
	}

	
	/**
	 * @return Der Quelltext der Dualis-Kalenderanzeige für den aktuellen Monat.
	 */
	private String getCalenderMonth(String args){
		URLConnection conn;
		try{
		conn = HTTP.connect(DUALIS_KALENDER_URL + args + DUALIS_KALENDER_MONATSANSICHT_ARGUMENTS_SUFFIX, null, null);
		} catch (Exception e) {
			return null;
		}
	    return HTTP.readURLConnection(conn);
	}
	

	

	/**
	 * Login-Funktion.
	 * Meldet sich per URLConnection am Dualis Server an. Dabei werden folgende Variablen verwendet:
	 * DUALIS_LOGIN_URL --> dies ist die URL zu der der POST Request geschickt wird
	 * DUALIS_COOKIE --> Unser Dauercookie. Es sieht so aus .... als wäre dualis scheiße!
	 * DUALIS_ARGUMENTE_REGEXP --> dieser Reguläre Ausdruck filter die Argumente für den Return Wert aus dem Header.
	 * 
	 * @return die Argumente die nun immer im Get HEADER übergeben werden müssen, um eingelogt zu bleiben. null, falls login fehlgeschlagen hat (kann verschiedene Gründe haben).
	 */
	
	private String login(String username, String passwort){
		String cookie=null;
		if(username==null || passwort==null)return null;
		try{
		// Construct data
		String data = "usrname="+username+"&pass="+passwort+"&APPNAME=CampusNet&PRGNAME=LOGINCHECK&ARGUMENTS=clino%2Cusrname%2Cpass%2Cmenuno%2Cpersno%2Cbrowser%2Cplatform&clino=000000000000001&menuno=000000&persno=00000000&browser=&platform=";
		
		URLConnection conn = HTTP.connect(DUALIS_LOGIN_URL, DUALIS_COOKIE, data);
	    String header=HTTP.getHeader(conn);
		
	    Pattern p = Pattern.compile (DUALIS_ARGUMENTE_REGEXP, Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher (header);
	    
	    if (m.find())cookie = m.group(1);

		} catch (Exception e) {
			//Es wird einfach kein cookie zurückgegeben. Login-failed
			cookie=null;
		}
		
		return cookie; //unser Argument String
	}
	

	
	private static String generateCookie(){
		String cookie="";
		for(int i=0;i<16;i++){
			cookie+=new Double(Math.random()*99).intValue();
		}
		return cookie;
	}


	@Override
	public boolean anmelden(Form formular) {
		if(formular.getElement("name")==null)return false;
		if(formular.getElement("pw")==null)return false;
		args = login(formular.getElement("name").getValue(), formular.getElement("pw").getValue());
		return (args!=null);
	}



	@Override
	/**
	 * Stellt das Formular, bestehend aus 2 Eingabefeldern für Benutzername und Passwort, her.
	 */
	public Form getFormular() {
		Form f = new Form();
		TextInput name = new TextInput("name");
		name.setPrompt("Dualis-Benutzername");
		f.add(name);
		TextInput pw = new TextInput("pw");
		pw.setPrompt("Passwort:");
		pw.setPWModeOn(true);
		f.add(pw);
		return f;
	}

}
