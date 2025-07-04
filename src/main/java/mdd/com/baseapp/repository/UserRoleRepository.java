package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  @Query(value = "select * from user_role where user_id = ?1 ", nativeQuery = true)
  Optional<UserRole> findByUserId(Integer id);
}
