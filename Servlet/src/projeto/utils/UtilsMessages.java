package projeto.utils;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class UtilsMessages {

	public static void message(Map<String, String> session, String remove, String put, String putMsg){
		if(session.get(remove) != null)
    		session.remove(remove);
		session.put(put, putMsg);
	}
	
    public static void messageRedirect(Map<String, String> session, String put, String putMsg, String remove, String pageRedirect, HttpServletResponse response) throws IOException{
    	
    	if(session.get(remove) != null)
    		session.remove(remove);
		session.put(put, putMsg);		
		response.sendRedirect(pageRedirect);
	}
}
