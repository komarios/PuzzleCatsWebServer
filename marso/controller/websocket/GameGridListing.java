package marso.controller.websocket;

protected class GameGridListing implements GameAction {
          public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {		
		PCResponse response = new PCResponse();
		if( pcmessage.getOppoId().equals("") )
			response.addMessage( pcmessage.getUserId(), "oppo_no_conn" );
		else
			response.addMessage( pcmessage.getOppoId(), pcmessage.getAction() );
		return response;
          }
}
