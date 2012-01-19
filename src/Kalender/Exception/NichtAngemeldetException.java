package Kalender.Exception;

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
