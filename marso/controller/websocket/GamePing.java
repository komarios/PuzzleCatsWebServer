package marso.controller.websocket;

public class GamePing implements GameAction {
	public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {		
		PCResponse response = new PCResponse();
		response.addMessage( pcmessage.getUserId(), "pong:"+pcmessage.getData() );
		//TODO: Handle ping from user-to-user 
		return response;
	}
}
