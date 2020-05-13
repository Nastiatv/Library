package by.runa.lib.api.exceptions;

public class NoUserWithThisIdException extends Exception {

    private static final long serialVersionUID = -4421070750329320342L;

    public NoUserWithThisIdException() {
        super("Sorry, no user with this id");
    }
}
