package by.runa.lib.exceptions;

public class NoUserWithThisIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4421070750329320342L;

	@Override
	public String getMessage() {
		return "Sorry, No User With This Id";
	}
}
