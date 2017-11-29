package marso.controller.websocket;

protected class GameAdminList implements GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{			
		sendMsgToClient( session, "sessionList:"+sessionList );
		sendMsgToClient( session, "reverseSessionList:"+reverseSessionList );
		sendMsgToClient( session, "gameStartList:"+gameStartList );
          }
}
