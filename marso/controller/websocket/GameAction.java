package marso.controller.websocket;

import org.springframework.web.socket.WebSocketSession;

public interface GameAction {
         public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId );
}
