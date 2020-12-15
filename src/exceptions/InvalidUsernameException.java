package exceptions;

public class InvalidUsernameException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidUsernameException() {
		super("Username is invalid.");
	}

}
