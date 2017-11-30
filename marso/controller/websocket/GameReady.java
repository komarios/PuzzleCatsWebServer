package marso.controller.websocket;

protected class GameReady implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{
		List<String[]> messages = new ArrayList<String[]>();
		if( oppoSessionId.equals("") )
			messages.add( new String[] { mySessionId, "oppo_no_conn" } );
		else {
			gameStartList.put( pcmessage.getUserId(), "ready");
			messages.add( new String[] { mySessionId, "readyok" } );
			if ( gameStartList.get( pcmessage.getOppoId() ) != null ) {
				messages.add( new String[] { mySessionId, "begin:7" } );
				messages.add( new String[] { oppoSessionId, "begin:7" } );
			}
		}
		return messages;
          }
}
