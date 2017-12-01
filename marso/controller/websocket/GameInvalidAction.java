package marso.controller.websocket;

protected class GameInvalidAction implements GameAction {
	  public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();
		response.addMessage( pcmessage.getUserId(), "Invalid Action" );
		return response;
          }
}
