package ro.hackitall.encode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.hackitall.encode.config.SecurityConfig;
import ro.hackitall.encode.dal.repo.RoleRepository;
import ro.hackitall.encode.dal.repo.UserRepository;
import ro.hackitall.encode.dal.model.RoleEntity;
import ro.hackitall.encode.dal.model.UserEntity;
import ro.hackitall.encode.service.interf.UserServiceInterface;
import ro.hackitall.encode.service.util.RegisterUserInit;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@Service
public class UserService implements UserServiceInterface {

    public final static String NAME = "UserService";
    private final static Logger logger = Logger.getLogger(UserService.class);


    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.findByUsername(username);
        if (userDetails == null)
            return null;

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (GrantedAuthority role : userDetails.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
    }
    @Override
    @Transactional
    @Secured(value = SecurityConfig.Roles.ROLE_ANONYMOUS)
    public UserEntity registerUser(RegisterUserInit init) {

        UserEntity userLoaded = userDao.findByUsername(init.getUserName());

        if (userLoaded == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(init.getUserName());
            userEntity.setEmail(init.getEmail());

            userEntity.setAuthorities(getUserRoles());
            // TODO firebase users should not be able to login via username and
            // password so for now generation of password is OK
            userEntity.setPassword(UUID.randomUUID().toString());
            userDao.save(userEntity);
            logger.info("registerUser -> user created");
            return userEntity;
        } else {
            logger.info("registerUser -> user exists");
            return userLoaded;
        }
    }

    @PostConstruct
    public void init() {

        if (userDao.count() == 0) {
            UserEntity adminEntity = new UserEntity();
            adminEntity.setUsername("admin");
            adminEntity.setPassword("admin");
            adminEntity.setEmail("andreidaniel.ene@gmail.com");

            adminEntity.setAuthorities(getAdminRoles());
            userDao.save(adminEntity);

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("user1");
            userEntity.setPassword("user1");
            userEntity.setEmail("andreidaniel.ene@gmail.com");
            userEntity.setAuthorities(getUserRoles());

            userDao.save(userEntity);
        }
    }

    private List<RoleEntity> getAdminRoles() {
        return Collections.singletonList(getRole(SecurityConfig.Roles.ROLE_ADMIN));
    }

    private List<RoleEntity> getUserRoles() {
        return Collections.singletonList(getRole(SecurityConfig.Roles.ROLE_USER));
    }

    private RoleEntity getRole(String authority) {
        RoleEntity adminRole = roleRepository.findByAuthority(authority);
        if (adminRole == null) {
            return new RoleEntity(authority);
        } else {
            return adminRole;
        }
    }
}
