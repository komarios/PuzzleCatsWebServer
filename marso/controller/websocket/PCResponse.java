package marso.controller.websocket;

protected class PCResponse {
        protected List<String[]> messages = new ArrayList<String[]>();
        public void addMessage(String userId, String msg){
                messages.add( new String[] { userId, msg } );
        }
}
