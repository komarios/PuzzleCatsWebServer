package marso.controller.websocket;

protected class GameReady implements GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else {
			gameStartList.put( pcmessage.getUserId(), "ready");
			sendMsgToClient( session,  "readyok" );
			if ( gameStartList.get( pcmessage.getOppoId() ) != null ) {
				sendMsgToClient( session,  "begin:7" );
				sendMsgToClient( oppoSession, "begin:7" );
			}
		}
          }
}
