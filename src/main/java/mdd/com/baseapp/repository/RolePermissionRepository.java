package mdd.com.baseapp.repository;

import mdd.com.baseapp.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the RolePermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
}
