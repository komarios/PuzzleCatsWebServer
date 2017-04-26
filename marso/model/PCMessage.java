package marso.model;

import org.springframework.web.socket.TextMessage;

public class PCMessage {
	public String message = "";
	public String user_id = "";
	public String action  = "";
	public String data    = "";
	public String oppo_id = "";
	public PCMessage( TextMessage textMessage ) throws Exception {
		message = new String(textMessage.asBytes());
		//     1_1&conn?1_2      1_1&break:1_1?1_2       lshg-lsvg-rshg-rsvg
		user_id = message.substring(                      0, message.indexOf("&") );//1_1
		action  = message.substring( message.indexOf("&")+1, message.indexOf("?") );//conn
		if( action.indexOf(":") >  -1 ){
			data    = action.substring( action.indexOf(":")+1, action.length() );//:1_1
			action  = message.substring( message.indexOf("&")+1, message.indexOf(":") );
		}
		oppo_id = message.substring( message.indexOf("?")+1, message.length()     );//1_2
		System.out.println( message+" "+user_id+" "+action+" "+oppo_id );
	}
}
