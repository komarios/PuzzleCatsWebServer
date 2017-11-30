package marso.controller.websocket;

protected class GameEnd implements GameAction {
          public List<String[]> execute( String mySessionId, PCMessage pcmessage, String oppoSessionId )
			throws InterruptedException, IOException{			
		List<String[]> messages = new ArrayList<String[]>();
		if( oppoSessionId.equals("") )
			messages.add( new String[] { mySessionId, "oppo_no_conn" } );
		else if( gameStartList.get( pcmessage.getOppoId() ) == null )
			messages.add( new String[] { mySessionId, "oppo_no_ready" } );
		else {
			messages.add( new String[] { oppoSessionId, "ko" } );
			messages.add( new String[] { mySessionId, "gg" } );
			messages.add( new String[] { oppoSessionId, "gg" } );
			gameStartList.remove(pcmessage.getUserId());
			gameStartList.remove(pcmessage.getOppoId());
		}
		return messages;
          }
}
