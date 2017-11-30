package marso.controller.websocket;

protected class GameEnd implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {			
		PCResponse response = new PCResponse();
		if( oppoSessionId.equals("") )
			response.addMessage( mySessionId, "oppo_no_conn" );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			response.addMessage( mySessionId, "oppo_no_ready" );
		else {
			response.addMessage( oppoSessionId, "ko" );
			response.addMessage( mySessionId, "gg" );
			response.addMessage( oppoSessionId, "gg" );
			gameStartList.remove(pcmessage.getUserId());
			gameStartList.remove(pcmessage.getOppoId());
		}
		return response;
          }
}
