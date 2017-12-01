package marso.controller.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEntryPoint extends TextWebSocketHandler {
	private static final Logger logger = Logger.getLogger(WebSocketEntryPoint.class);
	//<user_id, session>
	private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<String, WebSocketSession>();
	//<user_id, game_start>
	private final Map<String, String> gameStartList = new ConcurrentHashMap<String, String>();
	//<session_id, user_id>
	private final Map<String, String> reverseSessionList = new ConcurrentHashMap<String, String>();
	private final Map<String, GameAction> actions = new HashMap<>();
	
	public WebSocketEntryPoint(){
		super();
		actions.put("conn",  new GameConnect());
		actions.put("lshg",  new GameGridListing());
		actions.put("lsvg",  new GameGridListing());
		actions.put("rshg",  new GameGridListing());
		actions.put("rsvg",  new GameGridListing());
		actions.put("ready", new GameReady());
		actions.put("break", new GameBreak());
		actions.put("ping",  new GamePing());
		actions.put("ko",    new GameEnd());
		actions.put("InvalidAction",  new GameInvalidAction());
		actions.put("InvalidMessage", new GameInvalidMessage());
		//actions.put("adminlist", new GameAdminList());
	}
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info( "WebSocket was opened:"+ session.getId() );
	}
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage)
			throws InterruptedException, IOException {
		logger.info( "WebSocket client "+ session.getId() +" send message:"+  new String(textMessage.asBytes()) );
		PCMessage pcmessage;
		GameAction gameAction;
		PCResponse response;
		try{
			try{
				pcmessage = new PCMessage( textMessage );
				refreshUserLists( pcmessage.getUserId(), session );
				gameAction = actions.get( pcmessage.getAction() );
				if (gameAction == null)
					gameAction = actions.get( "InvalidAction" );
			} catch (InvalidMessageException e){
				logger.error("InvalidMessageException:", e);
				gameAction = actions.get( "InvalidMessage" );
			} 
			response = gameAction.execute( pcmessage, gameStartList.get( pcmessage.getOppoId() ) );
			for ( String[] message : response.messages )
				sendMsgToClient( sessionList.get( message[0] ), message[1] );
		 	for ( String[] statusUpdate : response.statusUpdates )
				updateUserGameStatus( statusUpdate[0],  statusUpdate[1] );
		} catch (InterruptedException e){
			logger.error("InterruptedException:", e);
			throw e;
		} catch (IOException e){
			logger.error("IOException:", e);
			throw e;
		} catch (Exception e){
			logger.error("Exception:", e);
		}
	}	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info( "WebSocket was closed:"+ session.getId() );
		String user_id = reverseSessionList.get( session.getId() );
		if ( user_id != null ) {
			if( sessionList.get(user_id) != null )
				sessionList.remove(user_id);
			reverseSessionList.remove(session.getId());
			//if( gameStartList.get(user_id) != null )
			//	gameStartList.remove(user_id);
			//TODO: fix game status
			logger.info( "WebSocket was cleaned up:"+user_id );
		}
		//TODO: handle reconnects
	}
	private PCResponse getAdminList( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();	
		response.addMessage( pcmessage.getUserId(), "sessionList:"+sessionList );
		response.addMessage( pcmessage.getUserId(), "reverseSessionList:"+reverseSessionList );
		response.addMessage( pcmessage.getUserId(), "gameStartList:"+gameStartList );
		return response;
	}
	private void refreshUserLists( String userid, WebSocketSession session ){
		sessionList.put(        userid,          session );
		reverseSessionList.put( session.getId(), userid  );
		//TODO: handle reconnects
	}
	private void updateUserGameStatus( String userId, String status ){
		gameStartList.put( userId,  status );
		if( status.equals("remove") )
			gameStartList.remove( userId );
	}
	private void sendMsgToClient( WebSocketSession session, String msg)
			throws InterruptedException, IOException {
		session.sendMessage( new TextMessage( msg ) );
	}
}
