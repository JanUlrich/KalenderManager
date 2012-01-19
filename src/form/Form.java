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
	 * Fügt ein FormElement der Form an.
	 * Es muss später über seinen Namen angesprochen werden.
	 * Aus diesem Grund dürfen keine gleichnamigen Elemente in einer Form vorkommen.
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
