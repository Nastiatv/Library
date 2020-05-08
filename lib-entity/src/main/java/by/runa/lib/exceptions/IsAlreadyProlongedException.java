package by.runa.lib.exceptions;

public class IsAlreadyProlongedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3616793790062667768L;

	@Override
	public String getMessage() {
		return "Sorry, This Book Is Already Prolonged";
	}
}
