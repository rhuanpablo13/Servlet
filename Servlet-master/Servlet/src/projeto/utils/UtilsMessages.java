package projeto.utils;

import java.io.IOException;
import java.io.PrintWriter;
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
    
    
    public static String showMessage(Map<String, String> session) {
    	if (session != null && session.get("erro") != null)
			return ("<div id='msg' style='color:red; text-align-last:center; '>" + session.get("erro") + "</div>");
		else if (session != null && session.get("sucesso") != null)
			return ("<div id='msg' style='color:green; text-align-last: center; '>" + session.get("sucesso") + "</div>");
		return "";
    }
    
    
    public static void showMessageWithTime(String msg, long time, PrintWriter out) {
		try {
			if (msg != null) {
				out.append(msg);
				System.out.println(msg);
			}
			Thread.sleep(time);
			
		} catch (InterruptedException e) {
			out.append("Erro! Erro ao mostrar mensagem!");
			e.printStackTrace();
		}
	}
}
