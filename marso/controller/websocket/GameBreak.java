package marso.controller.websocket;

protected class GameBreak implements GameAction {
	  public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{
		List<String[]> messages = new ArrayList<String[]>();
		if( oppoSessionId.equals("") )
			messages.add( new String[] { mySessionId, "oppo_no_conn" } );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			messages.add( new String[] { mySessionId, "oppo_no_ready" } );
		else
			messages.add( new String[] { oppoSessionId, pcmessage.getAction() } );
		//TODO: ACK: session.sendMessage( new TextMessage( "breakok:moveid" ) );
		return messages;
          }
}
