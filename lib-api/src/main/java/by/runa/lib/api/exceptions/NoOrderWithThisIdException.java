package by.runa.lib.api.exceptions;

public class NoOrderWithThisIdException extends Exception {

    private static final long serialVersionUID = -9150443334686313682L;

    public NoOrderWithThisIdException() {
        super("Sorry, no order with this id");
    }
}
