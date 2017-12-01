package marso.controller.websocket;

public class GameEnd implements GameAction {
          public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {		
		PCResponse response = new PCResponse();
		if( pcmessage.getOppoId().equals("") )
			response.addMessage( pcmessage.getUserId(), "oppo_no_conn" );
		else if( oppoStatus == null )
			response.addMessage( pcmessage.getUserId(), "oppo_no_ready" );
		else {
			response.addMessage( pcmessage.getOppoId(), "ko" );
			response.addMessage( pcmessage.getUserId(), "gg" );
			response.addMessage( pcmessage.getOppoId(), "gg" );
			response.addStatusUpdate( pcmessage.getUserId(), "remove" );
			response.addStatusUpdate( pcmessage.getOppoId(), "remove" );
		}
		return response;
          }
}
