package marso.controller.websocket;

public interface GameAction {
         public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession );
}
