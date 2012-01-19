package Kalender;


public class VorlesungsTermin{
	public String description;
	public String location;
	public Datum start;
	public Datum end;
	
	public VorlesungsTermin(){
		
	}
	/**
	 * Dieser Konstruktor kann nur verwendet werden, wenn die Paramter in terminDesc folgendermaßen übergeben werden:
	 * @param terminDesc == " Uhrzeit / Ort / Vorlesungsfach "
	 */
	public VorlesungsTermin(String date, String terminDesc){
		String a[] = terminDesc.split("/"); // ( Uhrzeit / Ort / Vorlesungsfach )
		description = terminDesc; //Vorlesungsfach
		location = a[1];
	}
	
	@Override
	public String toString(){
		return "am "+start+": "+description+"\n Endet am: "+end+"\n";
	}
}