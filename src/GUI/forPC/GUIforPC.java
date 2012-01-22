package GUI.forPC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import Kalender.Kalender;
import Kalender.VorlesungsTermin;
import Kalender.userInterface.VorlesungsTerminAnzeige;

import form.Form;
import form.FormElement;
import form.FormSubmitListener;
import form.FormView;
import form.TextInput;
import grafischeDarstellung.KalenderGUI;

/**
 * Diese Klasse bildet das GUI des Programms für Desktop-Rechner und Laptops. Allerdings nicht für Smartphones.
 * Alle Informationen zur Darstellung befinden sich in dieser Klasse.
 * Sie sollte nur über die bereitgestellten Schnittstellen angesteuert werden (KalenderGUI).
 * @author Andreas
 *
 */
public class GUIforPC extends JFrame implements KalenderGUI{
	private JTextField terminAnzeige;
	private JPanel formAnzeige;
	private JButton go;
	private ArrayList<FormSubmitListener> submitListeners;
	private Form angezeigteForm;
	
	public GUIforPC(String name){
		super(name);
		submitListeners = new ArrayList<FormSubmitListener>();
		//initComponents();
		init();
		setVisible(true);
	}

	@Override
	public void add(TextInput input) {
		if(formAnzeige==null){
			formAnzeige = new JPanel();
			formAnzeige.setLayout(new BoxLayout(formAnzeige,BoxLayout.PAGE_AXIS));
		}
		JTextField inputBox = new JTextField();
		inputBox.setPreferredSize(new Dimension(30,100));
		formAnzeige.add(inputBox);
	}
	
	
	@Override
	public void display(VorlesungsTermin termin) {
		terminAnzeige.setText(termin.toString());
	}
	
	public final void display(Form form){
		for(FormElement f : form.getElements()){
			if(f instanceof TextInput)add((TextInput)f);
		}
		
		//zum Schluss wird die Form angezeigt:
		getContentPane().add(formAnzeige, BorderLayout.CENTER);
		formAnzeige.add(go);
		formAnzeige.setVisible(true);
		pack(); 
		angezeigteForm = form; // und die Variable gesetzt!
	}
	
	private void init(){
		go = new JButton("go!");
		go.addActionListener(new SubmitActionListener());
		setLayout(new BorderLayout());
		terminAnzeige = new JTextField();
		this.getContentPane().add(terminAnzeige, BorderLayout.LINE_END);
		pack();
	}
	
	class SubmitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           submitForm();
        }
    } 
	
	private void submitForm(){
		//grafischer Teil:
		this.remove(formAnzeige);
		formAnzeige.removeAll();
		repaint();
		//================
		//alle SubmitListeners auslösen:
		for(FormSubmitListener l : submitListeners)if(angezeigteForm!=null)l.submit(angezeigteForm);
	}
	
	@Override
	public void addSubmitListener(FormSubmitListener onSubmit) {
		// TODO Auto-generated method stub
		
	}
	


}
