package todo.exception;

public class TodoException extends Exception {

	private static final long serialVersionUID = -8306543173313159012L;

	public TodoException(String message, Throwable cause) {
        super(message, cause);
    }

}
