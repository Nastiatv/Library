package by.runa.lib.api.exceptions;

public class NoDepartmentWithThisIdException extends Exception {

    private static final long serialVersionUID = -3277203110595708091L;

    public NoDepartmentWithThisIdException() {
        super("Sorry, no department with this id");
    }
}
