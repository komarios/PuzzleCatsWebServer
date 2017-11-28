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

@Component
public class WebSocketEntryPoint extends TextWebSocketHandler {
	private static final Logger logger = Logger.getLogger(WebSocketEntryPoint.class);
	
	private void handleInvalidMessage( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{
		session.sendMessage( new TextMessage( "Invalid Message:"+pcmessage.getMessage() ) );
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

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info( "WebSocket was opened:"+ session.getId() );
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
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
}
