package lt.GintautasButkus.Entity;


public class JwtResponse {
	
	private Client user;
	private String jwtToken;
	
	
	public JwtResponse(Client user, String jwtToken) {
		super();
		this.user = user;
		this.jwtToken = jwtToken;
	}


	public Client getUser() {
		return user;
	}


	public void setUser(Client user) {
		this.user = user;
	}


	public String getJwtToken() {
		return jwtToken;
	}


	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	

}
