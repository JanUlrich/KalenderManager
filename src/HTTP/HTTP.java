package HTTP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Diese Klasse stellt statische Funktionen zur Kommunikation per HTTP(s) bereit.
 * Sozusagen der WinHTTP-Ersatz. ^^
 * 
 * @author Andreas
 *
 */
public class HTTP {

	public static final String CHARSET = "UTF-8";
	

	/**
	 * @param surl Die URL zu der connected werden soll
	 * @param Cookie kann auch null sein, wenn kein Cookie gesendet werden soll
	 * @param content kann auch null sein, wenn kein content gesendet werden soll. Ist dann ein GET request
	 * @return
	 */
	public static URLConnection connect(String surl, String Cookie, String content) throws MalformedURLException, IOException{
		//try{
		URL url = new URL(surl);
	    URLConnection conn = url.openConnection();
	    //conn.setRequestProperty("Accept-Charset", CHARSET);
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8" );
	    conn.setRequestProperty("User-Agent", "by Andi S.");
	    if(Cookie!=null)conn.setRequestProperty("Cookie", Cookie);
	    if(content!=null){
			conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(content);
		    //wr.flush();
		    wr.close();
	    }
		//}catch(){
			
		//}
	    return conn;
	}
	
	/**
	 * @return Die vom Server empfangenen Daten (z.B. HTML-Dokument) ohne Header als String
	 */
	public static String readURLConnection(URLConnection conn){
		String ret="";
		try{
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	        ret+=line;
	    }
	    rd.close();
		} catch (Exception e) {
			ret=null;
		}
		return ret;
	}
	
	public static String getHeader(URLConnection con){
		String ret="";
		for(String field : con.getHeaderFields().keySet()){
			ret+=field+" : "+con.getHeaderFields().get(field)+"\n";
		}
		return ret;
	}
}
