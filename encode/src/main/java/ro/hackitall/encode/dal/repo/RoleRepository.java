package ro.hackitall.encode.dal.repo;

import org.springframework.data.repository.CrudRepository;
import ro.hackitall.encode.dal.model.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

	RoleEntity findByAuthority(String authority);
}
