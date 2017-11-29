package marso.controller.websocket;

import org.springframework.web.socket.WebSocketSession;

public interface GameAction {
         public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession );
}
