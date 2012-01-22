import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import GUI.forPC.GUIforPC;
import Kalender.Datum;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import form.Form;
import form.FormSubmitListener;
import form.TextInput;
import grafischeDarstellung.KalenderGUI;

import Kalender.Kalender;
import Kalender.VorlesungsTermin;
import Kalender.DualisKalender.DualisKalender;
import Kalender.Exception.NichtAngemeldetException;
import Kalender.HSRottenburgKalender.HSRottenburgKalender;

// http://www.fh-rottenburg.de/studium/stundenplan/html-untis/default.htm  <---- Beissis Stundenplan!

/**
 * Der KalenderManager soll Termine aus verschiedenen Kalendern, Stundenpl‰nen, Terminplanern etc. , welche in digitaler Form verf¸gbar sind, auslesen.
 * Die Ausgelesenen Daten kˆnnen anschlieﬂend angezeigt werden oder in einen anderen Kalender exportiert werden. (wie z.B. google Kalender).
 * Dies schafft eine bessere ‹bersicht spart Zeit. Auﬂerdem soll die Software Plattformunabh‰ngig und vor allem auch auf Smartphones lauff‰hig sein. 
 */
public class Programm{
	public static final String ROTTENBURG_KALENDER_REGEXP = "";
	private static final String FORMULAR_FENSTERNAMEN = "Fenster";
	private static final String PROG_NAME = "Kalender-Manager";
	private KalenderGUI window;
	public ArrayList<VorlesungsTermin> vorlesungen;
	private ArrayList<Kalender> usedKalenders;
	
	private Kalender kalender;

	
	public static void main(String args[]){
		new Programm();
		//new HSRottenburgKalender();
	}
	
	public Programm(){
		//JFrame fenster=createMainWindow();
		
		window = new GUIforPC(FORMULAR_FENSTERNAMEN); // das Hauptfenster
		usedKalenders = new ArrayList<Kalender>();
		//setToFormSubmitWindow(window);
		DualisKalender kal = new DualisKalender();
		usedKalenders.add(kal);
		kalenderAnmelden(kal);
	}
	
	class OnSubmit implements FormSubmitListener {
		public void submit(Form f) {
			
		}
    } 
	
	public void kalenderAnmelden(Kalender kal){
		Form f = kal.getFormular();
		window.display(f);
		window.addSubmitListener(new OnSubmit());
	}
	
	private JFrame createMainWindow(){
		JFrame window = new JFrame(PROG_NAME);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = window.getContentPane();
		content.setLayout(new BorderLayout(5,5));
		
		window.setVisible(true);
		window.pack();
		return window;
	}
	/**
	 * anschlieﬂend muss es vlt noch gepackt werden ...
	 * Funktion in Arbeit !
	 */
	private Container setToFormSubmitWindow(Container window){
		window.setLayout(new GridLayout(5,1));
        
        JButton button = new JButton("GO!");
        window.add(button);
        
		return window;
	}

	private String getUserPW(){
		JPasswordField pwd = new JPasswordField(10);  
	    int action = JOptionPane.showConfirmDialog(null, pwd,"Passwort",JOptionPane.OK_CANCEL_OPTION);  
	    if(action < 0)return null;  
	    else return new String(pwd.getPassword());  
		
	}
	


	


}


/*
String document="";
File file = new File("test.txt");
FileInputStream fis = null;
BufferedInputStream bis = null;
DataInputStream dis = null;

try {
  fis = new FileInputStream(file);

  // Here BufferedInputStream is added for fast reading.
  bis = new BufferedInputStream(fis);
  dis = new DataInputStream(bis);

  // dis.available() returns 0 if the file does not have more lines.
  while (dis.available() != 0) {

  // this statement reads the line from the file and print it to
    // the console.
   document+=dis.readLine();
  }

  // dispose all the resources after using them.
  fis.close();
  bis.close();
  dis.close();

} catch (FileNotFoundException e) {
  e.printStackTrace();
} catch (IOException e) {
  e.printStackTrace();
}
try {
	XPather test=new XPather(document);
	Node[] ret=test.getNodes("//a/node()");
	for(Node r:ret){
		System.out.println(r);
	} // hier wird das Ergebnis ausgegeben.
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	public Programm(){
		kalender=new DualisKalender();
		String username="*********";
		String passwort;
		
		String args;
		do{
			username=JOptionPane.showInputDialog("Dualis Loginname eingeben:",username);
			if(username==null)return;
			passwort=getUserPW();
			if(passwort==null)return;
		}while(!kalender.anmelden(username, passwort));
		System.out.println("Eingelogt: ");
		ArrayList<VorlesungsTermin> month = null;
		try {
			month = kalender.getVorlesungstermine(null, null);
		} catch (NichtAngemeldetException e) {
			System.out.println("Anmeldung fehlgeschlagen");
			return; //Anmelden bei DualisKalender abbrechen.
		}
		
		for(VorlesungsTermin s:month)System.out.println("Termine diesen Monat:"+s);

		
		
	    
	    System.out.println("Ende!");
	    /*
		try {
		    
		    //conn.setRequestProperty("Cookie", cookie);
		    // Get the response
		    //conn.getHeaderField(name);
		    
		    //System.out.println(getHeader(conn));
		    
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        System.out.println(line);
		    }
		    //wr.close();
		    rd.close();
		    
		} catch (Exception e) {
		}
		*/
		

