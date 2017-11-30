package marso.controller.websocket;

protected class GameGridListing implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{			
		List<String[]> messages = new ArrayList<String[]>();
		if( oppoSessionId.equals("") )
			messages.add( new String[] { mySessionId, "oppo_no_conn" } );
		else
			messages.add( new String[] { oppoSessionId, pcmessage.getAction() } );
		return messages;
          }
}
