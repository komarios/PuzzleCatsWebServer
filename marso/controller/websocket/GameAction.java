package marso.controller.websocket;

public interface GameAction {
        public PCResponse execute( PCMessage pcmessage, String oppoStatus );
}
