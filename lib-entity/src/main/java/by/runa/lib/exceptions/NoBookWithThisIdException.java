package by.runa.lib.exceptions;

public class NoBookWithThisIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4094104465212312256L;

	@Override
	public String getMessage() {
		return "Sorry, No Book With This Id";
	}
}
