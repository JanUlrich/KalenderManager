package grafischeDarstellung;

import form.FormView;
import Kalender.userInterface.VorlesungsTerminAnzeige;

/**
 * Die Kalender GUI muss bestimmte Aktionen ausf�hren k�nnen.
 * Sie ist nur als Schnittstelle (interface) und nicht als Klasse definiert, da ihre Funktionen f�r verschiedenen Plattformen anders implementiert werden m�ssen.
 * Sie f�hrt alle ben�tigten Interfaces, welche mit der Graphischen Darstellung zu tun haben, zusammen.
 * Eine GUI f�r den KalenderManager muss dieses Interface einbinden!
 * @author Andreas
 *
 */
public interface KalenderGUI extends VorlesungsTerminAnzeige, FormView {

}
