package form;

/**
 * Ein Element einer Form.
 * @author Andreas
 *
 */
public class TextInput implements FormElement{
	private String name;
	private String prompt;
	private String text;
	private boolean pwMode; 
	
	public TextInput(String name){
		this.name=name;
		this.prompt="";
		text="";
		pwMode=false;
	}
	/**
	 * Die Überschrift des Eingabefeldes setzen
	 * @param text - die Überschrift
	 */
	public void setPrompt(String text){
		prompt=text;
	}
	public String getPrompt(){
		return prompt;
	}
	/**
	 * Den im Eingabefeld stehenden Text setzen
	 * @param text
	 */
	public void setText(String text){
		this.text=text;
	}
	/**
	 * 
	 * @return - der Im Eingabefeld stehende Text.
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * wird dieser Funktion true übergeben, dann werden die Zeichen im Eingabefeld nicht angezeigt
	 * Eignet sich dann als Eingabefeld für ein Passwort.
	 */
	public void setPWModeOn(boolean pwMode){
		this.pwMode=pwMode;
	}
	/**
	 * 
	 * @return - true, falls Zeichen im Eingabefeld nicht angezeigt werden sollen.
	 */
	public boolean isPWModeOn(){
		return pwMode;
	}
	
	@Override
	public String getValue() {
		return text;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name=name;
	}
}
