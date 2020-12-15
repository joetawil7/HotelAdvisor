package entity;

public class CreditCard {

	private String cardNumber;
	private double balance;

	public CreditCard() {
	}

	public CreditCard(String cardNumber, double balance) {
		this.setCardNumber(cardNumber);
		this.setBalance(balance);
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
