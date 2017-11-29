package marso.controller.websocket;

protected class GameAdminList extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		sendMsgToClient( session, "sessionList:"+sessionList );
		sendMsgToClient( session, "reverseSessionList:"+reverseSessionList );
		sendMsgToClient( session, "gameStartList:"+gameStartList );
          }
}
