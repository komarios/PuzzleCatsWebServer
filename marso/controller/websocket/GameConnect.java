package marso.controller.websocket;

protected class GameConnect implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{			
		PCResponse response = new PCResponse();
		response.addMessage( mySessionId, "connok" );
		if ( ! oppoSessionId.equals("") ) {
			response.addMessage( mySessionId, "conn2ok" );
			response.addMessage( oppoSessionId, "conn2ok" );
		}
		return response;
          }
}
