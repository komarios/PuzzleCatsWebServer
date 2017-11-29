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

protected class ActionHandler {
	private static final Logger logger = Logger.getLogger(ActionHandler.class);
	//<user_id, session>
	private final Map<String, WebSocketSession> sessionList = new ConcurrentHashMap<String, WebSocketSession>();
	//<user_id, game_start>
	private final Map<String, String> gameStartList = new ConcurrentHashMap<String, String>();
	//<session_id, user_id>
	private final Map<String, String> reverseSessionList = new ConcurrentHashMap<String, String>();
	private final Map<Key, GameAction> actions = new HashMap<>();
	
	public ActionHandler(){
		actions.add("conn", new GameConnect());
		actions.add("lshg", new GameGridListing());
		actions.add("lsvg", new GameGridListing());
		actions.add("rshg", new GameGridListing());
		actions.add("rsvg", new GameGridListing());
		actions.add("ready", new GameReady());
		actions.add("break", new GameBreak());
		actions.add("ping", new GamePing());
		actions.add("ko", new GameEnd());
		actions.add("adminlist", new GameAdminList());
	}
	
	public void handleMessage(WebSocketSession session, TextMessage textMessage)
			throws InterruptedException, IOException {
		PCMessage pcmessage = null;
		try{
			pcmessage = new PCMessage( textMessage );
			pcmessage.logMessage();
			pcmessage.parseMessage();
			GameAction action = actions.get( pcmessage.getAction() );
			if (action == null) {
				throw new InvalidMessageException("Action not found :" + pcmessage.getAction() );
			}
			action.execute( session, pcmessage );
		} catch (InterruptedException e){
			logger.error("InterruptedException:", e);
			throw e;
		} catch (IOException e){
			logger.error("IOException:", e);
			throw e;
		} catch (InvalidMessageException e){
			logger.error("InvalidMessageException:", e);
			sendMsgToClient( session,  "InvalidMessageException:" + e.getMessage() );
		} catch (Exception e){
			logger.error("Exception:", e);
			sendMsgToClient( session, "Exception:" + e.getMessage() );
		}
	}
	
	private void handleInvalidMessage( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		session.sendMessage( new TextMessage( "Invalid Message:"+pcmessage.getMessage() ) );
	}
	
	protected void cleanUpOnDisconnect(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info( "WebSocket was closed:"+ session.getId() );
		String user_id = reverseSessionList.get( session.getId() );
		if ( user_id != null ) {
			if( sessionList.get(user_id) != null )
				sessionList.remove(user_id);
			if( gameStartList.get(user_id) != null )
				gameStartList.remove(user_id);
			reverseSessionList.remove(session.getId());
			logger.info( "WebSocket was cleaned up:"+user_id );
		}
	}
	
	private static void sendMsgToClient( WebSocketSession session, String msg){
		session.sendMessage( new TextMessage( msg ) );
	}
}
