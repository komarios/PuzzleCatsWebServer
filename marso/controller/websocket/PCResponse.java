package marso.controller.websocket;

public class PCResponse {
        List<String[]> messages = new ArrayList<String[]>();
        public void addMessage(String userId, String msg){
                messages.add( new String[] { userId, msg } );
        }
        public List<String[]> getMessages(){
                return messages;
        }
}
