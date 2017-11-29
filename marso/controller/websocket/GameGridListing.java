package marso.controller.websocket;

protected class GameGridListing extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		sessionList.put( pcmessage.getUserId(), session);
		reverseSessionList.put( session.getId(), pcmessage.getUserId() );
		sendMsgToClient( session, "connok" );
		if ( sessionList.get( pcmessage.getOppoId() ) != null ) {
			sendMsgToClient( session, "conn2ok" );
			WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
			sendMsgToClient( oppoSession, "conn2ok" );
		}
          }
}
