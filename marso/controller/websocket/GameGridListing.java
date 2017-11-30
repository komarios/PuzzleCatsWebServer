package marso.controller.websocket;

protected class GameGridListing implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {			
		PCResponse response = new PCResponse();
		if( oppoSessionId.equals("") )
			response.addMessage( mySessionId, "oppo_no_conn" );
		else
			response.addMessage( oppoSessionId, pcmessage.getAction() );
		return response;
          }
}
