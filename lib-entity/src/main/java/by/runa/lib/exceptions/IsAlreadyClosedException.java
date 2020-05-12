package by.runa.lib.exceptions;

public class IsAlreadyClosedException extends Exception {

	private static final long serialVersionUID = 8774948403114615216L;

	public IsAlreadyClosedException() {
        super("Sorry, This Order Is Already Closed");
    }
}
	

	