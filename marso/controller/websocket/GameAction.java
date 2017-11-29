package marso.controller.websocket;

protected interface GameAction {
         public void execute( WebSocketSession session, PCMessage pcmessage );
}
