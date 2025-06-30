package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * A UserRole.
 */
@Data
@Entity
@DynamicUpdate
@Table(
    name = "user_role",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}),
    indexes = {
        @Index(name = "idx_user_role_user_id", columnList = "user_id"),
        @Index(name = "idx_user_role_role_id", columnList = "role_id")
    }
)
public class UserRole extends AbstractAuditingEntity implements Serializable {

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "role_id")
  private Integer roleId;

  public UserRole() {
  }

  public UserRole(Integer userId, Integer roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserRole)) {
      return false;
    }
    return getId() != null && getId().equals(((UserRole) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "UserRole{" +
        "id=" + getId() +
        ", userId='" + getUserId() + "'" +
        ", roleId='" + getRoleId() + "'" +
        "}";
  }
}
