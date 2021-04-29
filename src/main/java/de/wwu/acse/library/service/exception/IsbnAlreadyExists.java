package de.wwu.acse.library.service.exception;

public class IsbnAlreadyExists extends Exception {
	private static final long serialVersionUID = 5792318529581693919L;

	public IsbnAlreadyExists() {
		super();
	}

	public IsbnAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IsbnAlreadyExists(String message, Throwable cause) {
		super(message, cause);
	}

	public IsbnAlreadyExists(String message) {
		super(message);
	}

	public IsbnAlreadyExists(Throwable cause) {
		super(cause);
	}
}
