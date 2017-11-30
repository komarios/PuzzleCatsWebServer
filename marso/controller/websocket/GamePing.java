package marso.controller.websocket;

protected class GamePing implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{			
		List<String[]> messages = new ArrayList<String[]>();
		messages.add( new String[] { mySessionId, "pong:"+pcmessage.getData()  } );
		//TODO: Handle ping from user-to-user 
		return messages;
          }
}
