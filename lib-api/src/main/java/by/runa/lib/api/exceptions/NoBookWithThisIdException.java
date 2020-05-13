package by.runa.lib.api.exceptions;

public class NoBookWithThisIdException extends Exception {

    private static final long serialVersionUID = -4094104465212312256L;

    public NoBookWithThisIdException() {
        super("Sorry, no book with this id");
    }
}
