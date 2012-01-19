package form;

import java.util.ArrayList;

import Kalender.VorlesungsTermin;

/**
 * Dieses Interface dient dazu, eine Form graphisch darzustellen.
 * F�r jedes FormElement-Objekt besteht eine display-Funktion.
 * Diese werden beim Aufrufen der FormElement.display-Funktion aufgerufen, wodurch die entsprechenden Elemente angezeigt werden k�nnen.
 * @author Andreas
 */
public interface FormView {
	public void display(TextInput input);
}

