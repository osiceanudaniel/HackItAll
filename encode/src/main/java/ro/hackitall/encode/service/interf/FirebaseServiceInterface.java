package ro.hackitall.encode.service.interf;

import ro.hackitall.encode.config.auth.firebase.FirebaseTokenHolder;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
public interface FirebaseServiceInterface {

    FirebaseTokenHolder parseToken(String idToken);
}
