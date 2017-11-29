package marso.controller.websocket;

protected class GamePing extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{			
		sendMsgToClient( session, "pong:"+pcmessage.getData() );
		//TODO: Handle ping from user-to-user 
          }
}
