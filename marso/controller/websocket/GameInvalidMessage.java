package marso.controller.websocket;

public class GameInvalidMessage implements GameAction {
	  public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();
		response.addMessage( pcmessage.getUserId(), "Invalid Message" );
		return response;
          }
}
