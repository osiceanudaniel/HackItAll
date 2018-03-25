package ro.hackitall.encode.service.interf;

import org.springframework.security.core.userdetails.UserDetailsService;
import ro.hackitall.encode.dal.model.UserEntity;
import ro.hackitall.encode.service.util.RegisterUserInit;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
public interface UserServiceInterface extends UserDetailsService {

    UserEntity registerUser(RegisterUserInit init);
}
