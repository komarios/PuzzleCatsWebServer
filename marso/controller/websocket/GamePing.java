package marso.controller.websocket;

protected class GamePing extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			sendMsgToClient( session, "oppo_no_ready" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
          }
}
