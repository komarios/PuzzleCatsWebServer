package marso.controller;

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
import marso.model.PCMessage;

@Component
public class GameMoveWSHandler extends TextWebSocketHandler {
	private static final Logger logger = Logger.getLogger(GameMoveWSHandler.class);
	//<user_id, session>
	private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<String, WebSocketSession>();
	//<user_id, game_start>
	private final Map<String, String> gameStartList = new ConcurrentHashMap<String, String>();
	//<session_id, user_id>
	private final Map<String, String> reverseSessionList = new ConcurrentHashMap<String, String>();
	
	private void handleConnect( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		sessionList.put( pcmessage.getUserId(), session);
		reverseSessionList.put( session.getId(), pcmessage.getUserId() );
		session.sendMessage( new TextMessage( "connok" ) );
		if ( sessionList.get( pcmessage.getOppoId() ) != null ) {
			session.sendMessage( new TextMessage( "conn2ok" ) );
			WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
			oppoSession.sendMessage( new TextMessage( "conn2ok" ) );
		}
	}
	
	private void handleGridList( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			session.sendMessage( new TextMessage( "oppo_no_conn" ) );
		else
			oppoSession.sendMessage( new TextMessage( pcmessage.getAction() ) );
	}
	
	private void handleReady( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			session.sendMessage( new TextMessage( "oppo_no_conn" ) );
		else {
			gameStartList.put( pcmessage.getUserId(), "ready");
			session.sendMessage( new TextMessage( "readyok" ) );
			if ( gameStartList.get( pcmessage.getOppoId() ) != null ) {
				session.sendMessage( new TextMessage( "begin:7" ) );
				oppoSession.sendMessage( new TextMessage( "begin:7" ) );
			}
		}
	}
	
	private void handleBreak( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			session.sendMessage( new TextMessage( "oppo_no_conn" ) );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			session.sendMessage( new TextMessage( "oppo_no_ready" ) );
		else
			oppoSession.sendMessage( new TextMessage( pcmessage.getAction() ) );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
	}
	
	private void handlePing( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		session.sendMessage( new TextMessage( "pong:"+pcmessage.getData() ) );
		//TODO: Handle ping from user-to-user 
	}
	
	private void handleKO( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			session.sendMessage( new TextMessage( "oppo_no_conn" ) );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			session.sendMessage( new TextMessage( "oppo_no_ready" ) );
		else {
			oppoSession.sendMessage( new TextMessage( "ko" ) );
			session.sendMessage( new TextMessage( "gg" ) );
			oppoSession.sendMessage( new TextMessage( "gg" ) );
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
		session.sendMessage( new TextMessage( "Invalid Message:"+pcmessage.getMessage() ) );
	}

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
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage)
			throws InterruptedException, IOException {
		PCMessage pcmessage = null;
		try{
			pcmessage = new PCMessage( textMessage );
			pcmessage.logMessage();
			if ( pcmessage.parseMessage() )
				handlePCMessage( session, pcmessage );
			else
				handleInvalidMessage( session, pcmessage );
		} catch (InterruptedException e){
			logger.error("InterruptedException:", e);
			throw e;
		} catch (IOException e){
			logger.error("IOException:", e);
			throw e;
		} catch (Exception e){
			logger.error("Exception:", e);
			session.sendMessage( new TextMessage( "Exception:" + e.getMessage() ) );
		}
	}
	private void handleAdminList( WebSocketSession session )
			throws InterruptedException, IOException{
		session.sendMessage( new TextMessage( "sessionList:"+sessionList ) );
		session.sendMessage( new TextMessage( "reverseSessionList:"+reverseSessionList ) );
		session.sendMessage( new TextMessage( "gameStartList:"+gameStartList ) );
	}
	/*
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	}
	*/		
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println( "WebSocket was closed" );
		String user_id = reverseSessionList.get( session.getId() );
		if ( user_id != null ) {
			if( sessionList.get(user_id) != null )
				sessionList.remove(user_id);
			if( gameStartList.get(user_id) != null )
				gameStartList.remove(user_id);
			reverseSessionList.remove(session.getId());
			System.out.println( "WebSocket was cleaned up:"+user_id );
		}
	}
}
