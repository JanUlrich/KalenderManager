package Kalender;

import java.util.ArrayList;
import java.util.Date;

import form.Form;

import Kalender.Exception.NichtAngemeldetException;


public interface Kalender{
/**
 * Dieser Funktion muss das ausgefüllte Formular übergeben werden. Um die anmeldung durchzuführen.
 * @param formular
 * @return true falls anmeldung funktioniert hat. False , falls das Formular nicht korrekt ausgefüllt wurde.
 */
public boolean anmelden(Form formular);
/**
 * Diese Funktion übergibt ein Formular in welches die zur Anmeldung benötigten Funktionen eingetragen werden müssen.
 * Oder null, falls keine Informationen nötig sind.
 * @return - Das Formular oder null
 */
public Form getFormular();


public ArrayList<VorlesungsTermin> getVorlesungstermine(Datum von, Datum bis) throws NichtAngemeldetException;

}
