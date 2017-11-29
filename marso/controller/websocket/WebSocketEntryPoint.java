package marso.controller.websocket;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;

@Component
public class WebSocketEntryPoint extends TextWebSocketHandler {
	private static final Logger logger = Logger.getLogger(WebSocketEntryPoint.class);
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info( "WebSocket was opened:"+ session.getId() );
	}
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage textMessage)
			throws InterruptedException, IOException {
		logger.info( "WebSocket client "+ session.getId() +" send message:"+  new String(textMessage.asBytes()) );
		ActionHandler.INSTANCE.handleTextMessage( session, textMessage );
	}	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info( "WebSocket was closed:"+ session.getId() );
		cleanUpOnDisconnect( session, status);
	}
}
