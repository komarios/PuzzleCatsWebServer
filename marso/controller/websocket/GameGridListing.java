package marso.controller.websocket;

protected class GameGridListing extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
          }
}
