package marso.controller.websocket;

import org.springframework.web.socket.WebSocketSession;

public interface GameAction {
        public PCResponse execute( PCMessage pcmessage, String oppoStatus );
}
