package exceptions;

public class CardInUseException extends Exception {

	private static final long serialVersionUID = 1L;

	public CardInUseException() {
		super("The entered credit card is already in use.");
	}
}
