package marso.controller.websocket;

protected class GameAdminList implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{
		List<String[]> messages = new ArrayList<String[]>();			
		messages.add( new String[] { mySessionId, "sessionList:"+sessionList } );
		messages.add( new String[] { mySessionId, "reverseSessionList:"+reverseSessionList } );
		messages.add( new String[] { mySessionId, "gameStartList:"+gameStartList } );
		return messages;
          }
}
