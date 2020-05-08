package by.runa.lib.exceptions;

public class NoDepartmentWithThisIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3277203110595708091L;

	@Override
	public String getMessage() {
		return "Sorry, No Department With This Id";
	}
}
