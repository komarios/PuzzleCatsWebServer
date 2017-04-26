package marso.model;

public class Pinger {
        private String server_time;
	private String client_ip;

        public String getserver_time(){
                return server_time;
        }
        public void setserver_time(String server_time){
                this.server_time = server_time;
        }
        public String getclient_ip(){
                return client_ip;
        }
        public void setclient_ip(String client_ip){
                this.client_ip = client_ip;
        }		
}
