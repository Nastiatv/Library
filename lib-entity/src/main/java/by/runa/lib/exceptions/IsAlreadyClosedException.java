package by.runa.lib.exceptions;

public class IsAlreadyClosedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8774948403114615216L;

	@Override
	public String getMessage() {
		return "Sorry, This Order Is Already Closed";
	}
}
