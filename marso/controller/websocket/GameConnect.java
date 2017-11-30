package marso.controller.websocket;

protected class GameConnect implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{			
		List<String[]> messages = new ArrayList<String[]>();
		messages.add( new String[] { mySessionId, "connok" );
		if ( ! oppoSessionId.equals("") ) {
			messages.add( new String[] { mySessionId, "conn2ok" );
			messages.add( new String[] { oppoSessionId, "conn2ok" );
		}
		return messages;
          }
}
