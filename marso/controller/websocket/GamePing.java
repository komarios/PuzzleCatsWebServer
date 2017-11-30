package marso.controller.websocket;

protected class GamePing implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {			
		PCResponse response = new PCResponse();
		response.addMessage( mySessionId, "pong:"+pcmessage.getData() );
		//TODO: Handle ping from user-to-user 
		return response;
          }
}
