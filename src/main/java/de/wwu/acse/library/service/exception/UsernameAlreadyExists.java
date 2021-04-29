package de.wwu.acse.library.service.exception;

public class UsernameAlreadyExists extends Exception {
	private static final long serialVersionUID = -9139210083920235215L;

	public UsernameAlreadyExists() {
		super();
	}

	public UsernameAlreadyExists(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameAlreadyExists(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameAlreadyExists(String message) {
		super(message);
	}

	public UsernameAlreadyExists(Throwable cause) {
		super(cause);
	}
}
