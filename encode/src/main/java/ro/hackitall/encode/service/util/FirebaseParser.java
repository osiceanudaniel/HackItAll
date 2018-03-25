package ro.hackitall.encode.service.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import ro.hackitall.encode.config.auth.firebase.FirebaseTokenHolder;
import ro.hackitall.encode.service.exception.FirebaseTokenInvalidException;
import ro.hackitall.encode.util.StringUtil;

public class FirebaseParser {
	public FirebaseTokenHolder parseToken(String idToken) {
		if (StringUtil.isBlank(idToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		try {
//			final FirebaseAuth instance = FirebaseAuth.getInstance();
//			Task<FirebaseToken> authTask = instance.verifyIdToken(idToken);
//
//			Tasks.await(authTask);

			FirebaseToken response = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();

			return new FirebaseTokenHolder(response);
		} catch (Exception e) {
			throw new FirebaseTokenInvalidException(e.getMessage());
		}
	}
}
