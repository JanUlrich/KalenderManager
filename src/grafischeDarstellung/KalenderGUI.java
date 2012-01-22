package grafischeDarstellung;

import form.FormView;
import Kalender.userInterface.VorlesungsTerminAnzeige;

/**
 * Die Kalender GUI muss bestimmte Aktionen ausführen können.
 * Sie ist nur als Schnittstelle (interface) und nicht als Klasse definiert, da ihre Funktionen für verschiedenen Plattformen anders implementiert werden müssen.
 * Sie führt alle benötigten Interfaces, welche mit der Graphischen Darstellung zu tun haben, zusammen.
 * Eine GUI für den KalenderManager muss dieses Interface einbinden!
 * @author Andreas
 *
 */
public interface KalenderGUI extends VorlesungsTerminAnzeige, FormView {

}
