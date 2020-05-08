package by.runa.lib.exceptions;

public class NoFeedbackWithThisIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4298301434288642348L;

	@Override
	public String getMessage() {
		return "Sorry, No Feedback With This Id";
	}
}
