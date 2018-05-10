package marso.model;

public class Vue {
	private String fname;
	private String lname;

        public Vue (){
                super();
	}

	public Vue (String fname, String lname){
		super();
		this.fname = fname;
		this.lname = lname;
	}
	
	public String getfname(){
			return fname;
	}
	public void setfname(String fname){
			this.fname = fname;
	}
	public String getlname(){
			return lname;
	}
	public void setlname(String lname){
			this.lname = lname;
	}
}

