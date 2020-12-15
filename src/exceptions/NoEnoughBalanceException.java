package exceptions;

public class NoEnoughBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoEnoughBalanceException() {
		super("No enough balance.");
	}

}
