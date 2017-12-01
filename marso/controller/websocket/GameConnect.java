package marso.controller.websocket;

public class GameConnect implements GameAction {
          public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {			
		PCResponse response = new PCResponse();
		response.addMessage( pcmessage.getUserId(), "connok" );
		//TODO: player matchup for a new game, code missing 
		if ( ! oppoSessionId.equals("") ) {
			response.addMessage( pcmessage.getUserId(), "conn2ok" );
			response.addMessage( pcmessage.getOppoId(), "conn2ok" );
		}
		return response;
          }
}
