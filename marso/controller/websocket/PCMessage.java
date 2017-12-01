package marso.controller.websocket;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PCMessage {
	private static final Logger logger = Logger.getLogger(PCMessage.class);
	private String messagePattern = "^\\d.*[_]\\d.*[&][a-z].*([:](.*))?[\\?]\\d.*[_]\\d.*$";
	private Pattern pattern = Pattern.compile( messagePattern );
	private String message = "";
	private String user_id = "";
	private String action  = "unknown";
	private String data    = "";
	private String oppo_id = "";
	
	public PCMessage( TextMessage textMessage ) throws Exception {
		message = new String(textMessage.asBytes());
		logger.info( "PCMessage_is:"+this.toString() );
		parseMessage();
	}
	private void debugMatchAndPrint(String mp){
		Pattern myPattern = Pattern.compile( mp );
		Matcher myMatcher = myPattern.matcher( message );
		logger.warn( "PCMessage_matcher:"+myMatcher.matches()+"="+mp );
	}
	public boolean isValid(){
		Matcher matcher = pattern.matcher( message );
		return matcher.matches();
	}
	private void parseMessage(){
		try{
			user_id = message.substring(                      0, message.indexOf("&") );
			action  = message.substring( message.indexOf("&")+1, message.indexOf("?") );
			if( action.indexOf(":") >  -1 ){
				data    = action.substring( action.indexOf(":")+1, action.length() );
				action  = message.substring( message.indexOf("&")+1, message.indexOf(":") );
			}
			oppo_id = message.substring( message.indexOf("?")+1, message.length()     );
		} catch ( Exception e ) {
			logger.error( "Exception in PCMessage_parseMessage:"+e.getMessage()+", and message = "+message );
			e.printStackTrace();
			throw new InvalidMessageException( "Exception Parsing Message : " + e.getMessage() );
		}
	}
	public String getMessage(){
		return message;
	}
	public String getUserId(){
		return user_id;
	}
	public String getAction(){
		return action;
	}
	public String getData(){
		return data;
	}
	public String getOppoId(){
		return oppo_id;
	}
	public String toString(){
		return (message+":"+user_id+"&"+action+":"+data+"?"+oppo_id);
	}
}
