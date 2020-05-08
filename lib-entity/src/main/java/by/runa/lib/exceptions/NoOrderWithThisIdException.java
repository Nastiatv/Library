package by.runa.lib.exceptions;

public class NoOrderWithThisIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9150443334686313682L;

	@Override
	public String getMessage() {
		return "Sorry, No Order With This Id";
	}
}
