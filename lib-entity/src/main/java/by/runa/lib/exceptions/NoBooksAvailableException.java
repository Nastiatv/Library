package by.runa.lib.exceptions;

public class NoBooksAvailableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5436303797788043366L;

	@Override
	public String getMessage() {
		return "Sorry, This Book Is Not Available Now";
	}
}
