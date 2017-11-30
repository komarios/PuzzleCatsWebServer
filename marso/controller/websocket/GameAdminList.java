package marso.controller.websocket;

protected class GameAdminList implements GameAction {
          public PCResponse execute( String mySessionId, PCMessage pcmessage, String oppoSessionId ) {
		PCResponse response = new PCResponse();	
		response.addMessage( mySessionId, "sessionList:"+sessionList );
		response.addMessage( mySessionId, "reverseSessionList:"+reverseSessionList );
		response.addMessage( mySessionId, "gameStartList:"+gameStartList );
		return response;
          }
}
