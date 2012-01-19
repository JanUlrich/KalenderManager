package Kalender.Exception;

/**
 * wird versucht von einem Kalender Daten abzurufen, welcher noch nicht "angemeldet" wurde bzw. nicht mehr angemeldet ist,
 * dann fliegt diese Exception
 * 
 * @author Andreas
 */
public class NichtAngemeldetException extends Throwable {
	String message;
	public NichtAngemeldetException(){
		message="Dieser Kalender erfordert zuerst eine Anmeldung.";
	}
	
	@Override
	public String getMessage(){
		return super.getMessage()+"\n"+message;
	}
}
