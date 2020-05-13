package by.runa.lib.api.exceptions;

public class NoFeedbackWithThisIdException extends Exception {

    private static final long serialVersionUID = 4298301434288642348L;

    public NoFeedbackWithThisIdException() {
        super("Sorry, no feedback with this id");
    }
}
