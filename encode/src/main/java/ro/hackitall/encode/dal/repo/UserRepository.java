package ro.hackitall.encode.dal.repo;

import org.springframework.data.repository.CrudRepository;
import ro.hackitall.encode.dal.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
