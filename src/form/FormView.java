package form;

import java.util.ArrayList;

import Kalender.VorlesungsTermin;

/**
 * Dieses Interface dient dazu, eine Form graphisch darzustellen.
 * Für jedes FormElement-Objekt besteht eine display-Funktion.
 * Diese werden beim Aufrufen der FormElement.display-Funktion aufgerufen, wodurch die entsprechenden Elemente angezeigt werden können.
 * @author Andreas
 */
public interface FormView {
	public void add(TextInput input);
	public void display(Form f);
	public void addSubmitListener(FormSubmitListener onSubmit);
}

