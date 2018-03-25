package ro.hackitall.encode.service;

import org.springframework.stereotype.Service;
import ro.hackitall.encode.config.auth.firebase.FirebaseTokenHolder;
import ro.hackitall.encode.service.interf.FirebaseServiceInterface;
import ro.hackitall.encode.service.util.FirebaseParser;
import ro.hackitall.encode.spring.coditional.FirebaseCondition;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@Service
@FirebaseCondition
public class FirebaseService implements FirebaseServiceInterface {
    @Override
    public FirebaseTokenHolder parseToken(String firebaseToken) {
        return new FirebaseParser().parseToken(firebaseToken);
    }

}
