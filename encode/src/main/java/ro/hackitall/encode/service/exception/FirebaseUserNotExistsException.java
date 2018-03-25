package ro.hackitall.encode.service.exception;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class FirebaseUserNotExistsException extends AuthenticationCredentialsNotFoundException {

	public FirebaseUserNotExistsException() {
		super("Customer Not Fount");
	}

	private static final long serialVersionUID = 789949671713648425L;
}
