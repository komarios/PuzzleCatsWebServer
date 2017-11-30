package marso.controller.websocket;

protected class GameAdminList implements GameAction {
          public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();	
		response.addMessage( pcmessage.getUserId(), "sessionList:"+sessionList );
		response.addMessage( pcmessage.getUserId(), "reverseSessionList:"+reverseSessionList );
		response.addMessage( pcmessage.getUserId(), "gameStartList:"+gameStartList );
		return response;
          }
}
