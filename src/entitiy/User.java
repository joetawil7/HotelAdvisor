package entity;

public class User {

	private String username;
	private CreditCard creditCard;
	
	public User() {	
	}
	
	public User (String username, CreditCard creditC) {
		this.setUsername(username);
		this.setCreditCard(creditC);
	}
	
	public User(String username) {
		this.setUsername(username);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return this.username;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
