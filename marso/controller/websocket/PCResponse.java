package marso.controller.websocket;

import java.util.List;
import java.util.ArrayList;

public class PCResponse {
        protected List<String[]> messages = new ArrayList<String[]>();
        public void addMessage(String userId, String msg){
                messages.add( new String[] { userId, msg } );
        }
        protected List<String[]> statusUpdates = new ArrayList<String[]>();
        public void addStatusUpdate(String userId, String status){
                messages.add( new String[] { userId, status } );
        }
}
