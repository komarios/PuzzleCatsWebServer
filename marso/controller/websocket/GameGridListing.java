package marso.controller.websocket;

protected class GameGridListing implements GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{			
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
          }
}
