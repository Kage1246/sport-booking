package mdd.com.baseapp.repository;

import java.util.Optional;
import mdd.com.baseapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByCode(String code);

  Optional<Role> findRoleById(Integer id);

  @Query(value = "select id from role where code = ?1", nativeQuery = true)
  Integer findIdByCode(String code);

  //    @Query(
  //            value = "select id id, code code, parent_code parentCode, name name, description description from Permission",
  //            nativeQuery = true
  //    )
  //    List<PermissionItemResponse> getAllPermission();
}
