package marso.controller.websocket;

protected class GameReady implements GameAction {
          public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();
		if( pcmessage.getOppoId().equals("") )
			response.addMessage( pcmessage.getUserId(), "oppo_no_conn" );
		else {
			response.addStatusUpdate( pcmessage.getUserId(), "ready" );
			response.addMessage( pcmessage.getUserId(), "readyok" );
			if ( oppoStatus!= null ) {
				response.addMessage( pcmessage.getUserId(), "begin:7" );
				response.addMessage( pcmessage.getOppoId(), "begin:7" );
			}
		}
		return response;
          }
}
