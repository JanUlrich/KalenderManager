package form;

import java.util.ArrayList;
import java.util.HashMap;

public class Form {
	private ArrayList<FormElement> elements;

	public Form(){
		elements=new ArrayList<FormElement>();
	}
	
	public FormElement getElement(String name){
		for(FormElement f : elements)if(f.getName().equals(name))return f;
		return null;
	}
	
	/**
	 * F�gt ein FormElement der Form an.
	 * Es muss sp�ter �ber seinen Namen angesprochen werden.
	 * Aus diesem Grund d�rfen keine gleichnamigen Elemente in einer Form vorkommen.
	 */
	public void add(FormElement toAdd){
		if(getElement(toAdd.getName())!=null)return;
		elements.add(toAdd);
	}
	
	public final void display(FormView gui){
		for(FormElement f : elements){
			if(f instanceof TextInput)gui.display((TextInput)f);
		}
	}
}
