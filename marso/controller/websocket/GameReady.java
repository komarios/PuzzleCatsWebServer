package marso.controller.websocket;

protected class GameReady implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {
		PCResponse response = new PCResponse();
		if( oppoSessionId.equals("") )
			response.addMessage( mySessionId, "oppo_no_conn" );
		else {
			response.addStatusUpdate( pcmessage.getUserId(), "ready" );
			response.addMessage( mySessionId, "readyok" );
			if ( gameStartList.get( pcmessage.getOppoId() ) != null ) {
				response.addMessage( mySessionId, "begin:7" );
				response.addMessage( oppoSessionId, "begin:7" );
			}
		}
		return response;
          }
}
