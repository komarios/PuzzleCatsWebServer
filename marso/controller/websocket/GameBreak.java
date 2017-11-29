package marso.controller.websocket;

protected class GameBreak implements GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage, WebSocketSession oppoSession )
			throws InterruptedException, IOException{			
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			sendMsgToClient( session, "oppo_no_ready" );
		else
			sendMsgToClient( oppoSession, pcmessage.getAction() );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
          }
}
