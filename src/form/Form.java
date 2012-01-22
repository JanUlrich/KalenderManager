package form;

import java.util.ArrayList;
import java.util.HashMap;

import Kalender.Kalender;

public class Form {
	private ArrayList<FormElement> elements;
	private Kalender from;
	
	public Form(Kalender from){
		this.from = from;
		elements=new ArrayList<FormElement>();
	}
	
	public FormElement getElement(String name){
		for(FormElement f : elements)if(f.getName().equals(name))return f;
		return null;
	}
	
	public ArrayList<FormElement> getElements(){
		return elements;
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
}
