package Kalender;

import java.util.ArrayList;
import java.util.Date;

import form.Form;

import Kalender.Exception.NichtAngemeldetException;


public interface Kalender{
/**
 * Dieser Funktion muss das ausgef�llte Formular �bergeben werden. Um die anmeldung durchzuf�hren.
 * @param formular
 * @return true falls anmeldung funktioniert hat. False , falls das Formular nicht korrekt ausgef�llt wurde.
 */
public boolean anmelden(Form formular);
/**
 * Diese Funktion �bergibt ein Formular in welches die zur Anmeldung ben�tigten Funktionen eingetragen werden m�ssen.
 * Oder null, falls keine Informationen n�tig sind.
 * @return - Das Formular oder null
 */
public Form getFormular();


public ArrayList<VorlesungsTermin> getVorlesungstermine(Datum von, Datum bis) throws NichtAngemeldetException;

}
