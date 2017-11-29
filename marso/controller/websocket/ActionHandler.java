package marso.controller.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionHandler {
	private static final Logger logger = Logger.getLogger(ActionHandler.class);
	//<user_id, session>
	private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<String, WebSocketSession>();
	//<user_id, game_start>
	private final Map<String, String> gameStartList = new ConcurrentHashMap<String, String>();
	//<session_id, user_id>
	private final Map<String, String> reverseSessionList = new ConcurrentHashMap<String, String>();
	
	public void handlePCMessage(WebSocketSession session, PCMessage pcmessage)
			throws InterruptedException, IOException, Exception {
		switch( pcmessage.getAction() ) {
			case "adminlist":
				handleAdminList( session );
				break;
			case "conn" :
				handleConnect( session, pcmessage );
				break;
			case "lshg" :
			case "lsvg" :
			case "rshg" :
			case "rsvg" :
				handleGridList( session, pcmessage );
				break;
			case "ready" :
				handleReady( session, pcmessage );
				break;
			case "break" :
				handleBreak( session, pcmessage );
				break;
			case "ping" :
				handlePing( session, pcmessage );
				break;	
			case "ko" :
				handleKO( session, pcmessage );
				break;
			default :
				handleInvalidMessage( session, pcmessage );
		}
	}
	
	private void handleConnect( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		sessionList.put( pcmessage.getUserId(), session);
		reverseSessionList.put( session.getId(), pcmessage.getUserId() );
		sendMsgToClient( session, "connok" );
		if ( sessionList.get( pcmessage.getOppoId() ) != null ) {
			sendMsgToClient( session, "conn2ok" );
			WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
			sendMsgToClient( oppoSession, "conn2ok" );
		}
	}
	
	private void handleGridList( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
	}
	
	private void handleReady( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else {
			gameStartList.put( pcmessage.getUserId(), "ready");
			sendMsgToClient( session,  "readyok" );
			if ( gameStartList.get( pcmessage.getOppoId() ) != null ) {
				sendMsgToClient( session,  "begin:7" );
				sendMsgToClient( oppoSession, "begin:7" );
			}
		}
	}
	
	private void handleBreak( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			sendMsgToClient( session, "oppo_no_ready" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
	}
	
	private void handlePing( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		sendMsgToClient( session, "pong:"+pcmessage.getData() );
		//TODO: Handle ping from user-to-user 
	}
	
	private void handleKO( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			sendMsgToClient( session, "oppo_no_ready" );
		else {
			sendMsgToClient( oppoSession, "ko" );
			sendMsgToClient( session, "gg" );
			sendMsgToClient( oppoSession, "gg" );
			sessionList.remove(pcmessage.getUserId());
			sessionList.remove(pcmessage.getOppoId());
			gameStartList.remove(pcmessage.getUserId());
			gameStartList.remove(pcmessage.getOppoId());
			reverseSessionList.remove(session.getId());
			reverseSessionList.remove(oppoSession.getId());
		}
	}
	
	private void handleInvalidMessage( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		sendMsgToClient( session, "Invalid Message:"+pcmessage.getMessage() ) );
	}
	
	private void handleAdminList( WebSocketSession session )
			throws InterruptedException, IOException{
		sendMsgToClient( session, "sessionList:"+sessionList );
		sendMsgToClient( session, "reverseSessionList:"+reverseSessionList );
		sendMsgToClient( session, "gameStartList:"+gameStartList );
	}
	
	private void sendMsgToClient( WebSocketSession session, String msg){
		session.sendMessage( new TextMessage( msg ) );
	}
}
