package mdd.com.baseapp.repository;


import java.util.List;
import java.util.Optional;
import mdd.com.baseapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findOneByUsername(String lowercaseLogin);

  List<User> findAllByUsername(String username);

  @Query("select u from User u where u.username = ?1 and u.isDelete = true ")
  Optional<User> findOneByUsernameActive(String lowercaseLogin);

  @Query(value =
      "SELECT DISTINCT p.code " +
          "FROM user_app u " +
          "JOIN user_role ur ON u.id = ur.user_id " +
          "JOIN role r ON ur.role_id = r.id " +
          "JOIN role_permission rp ON r.id = rp.role_id " +
          "JOIN permission p ON rp.permission_id = p.id " +
          "WHERE u.id = :userId ", nativeQuery = true)
  List<String> findPermissionsByUserId(@Param("userId") Integer userId);

}
