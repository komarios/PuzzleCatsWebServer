package marso.controller.websocket;

protected class GameEnd extends GameAction {
          public void execute( WebSocketSession session, PCMessage pcmessage )
			throws InterruptedException, IOException{			
		WebSocketSession oppoSession = sessionList.get( pcmessage.getOppoId() );
		if( oppoSession == null )
			sendMsgToClient( session, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			sendMsgToClient( session, "oppo_no_ready" );
		else {
			sendMsgToClient( oppoSession, "ko" );
			sendMsgToClient( session, "gg" );
			sendMsgToClient( oppoSession, "gg" );
			sessionList.remove(pcmessage.getUserId());
			sessionList.remove(pcmessage.getOppoId());
			gameStartList.remove(pcmessage.getUserId());
			gameStartList.remove(pcmessage.getOppoId());
			reverseSessionList.remove(session.getId());
			reverseSessionList.remove(oppoSession.getId());
		}
          }
}
