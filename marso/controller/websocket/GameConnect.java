package marso.controller.websocket;

protected class GameConnect implements GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{			
		sendMsgToClient( session, "connok" );
		if ( sessionList.get( pcmessage.getOppoId() ) != null ) {
			sendMsgToClient( session, "conn2ok" );
			sendMsgToClient( oppoSession, "conn2ok" );
		}
          }
}
