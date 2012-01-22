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
	
	//============================================================================
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Find");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 488, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 274, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>               
    
    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration  

	@Override
	public void addSubmitListener(FormSubmitListener onSubmit) {
		// TODO Auto-generated method stub
		
	}


}
