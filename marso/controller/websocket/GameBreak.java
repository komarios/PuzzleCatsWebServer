package marso.controller.websocket;

protected class GameBreak implements GameAction {
	  public PCResponse execute( PCMessage pcmessage, String oppoStatus ) {
		PCResponse response = new PCResponse();
		if( pcmessage.getOppoId().equals("") )
			response.addMessage( pcmessage.getUserId(), "oppo_no_conn" );
		else if( oppoStatus == null )
			response.addMessage( pcmessage.getUserId(), "oppo_no_ready" );
		else
			response.addMessage( pcmessage.getOppoId(), pcmessage.getAction() );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
		return response;
          }
}
