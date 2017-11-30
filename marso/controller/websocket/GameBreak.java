package marso.controller.websocket;

protected class GameBreak implements GameAction {
	  public PCResponse  execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {
		PCResponse response = new PCResponse();
		if( oppoSessionId.equals("") )
			response.addMessage( mySessionId, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			response.addMessage( mySessionId, "oppo_no_ready" );
		else
			response.addMessage( oppoSessionId, pcmessage.getAction() );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
		return response;
          }
}
