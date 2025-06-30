package mdd.com.baseapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@JsonIgnoreProperties(value = {"new"})
@Entity
@DynamicUpdate
@Table(
    name = "role_permission",
    uniqueConstraints = @UniqueConstraint(columnNames = {"permission_id", "role_id"}),
    indexes = {
        @Index(name = "idx_role_permission_permission_id", columnList = "permission_id"),
        @Index(name = "idx_role_permission_role_id", columnList = "role_id")
    }
)
public class RolePermission extends AbstractAuditingEntity implements Serializable {

  @Column(name = "role_id")
  private Integer roleId;


  @Column(name = "permission_id")
  private Integer permissionId;


  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "RolePermission{" +
        "id=" + getId() +
        ", roleId='" + getRoleId() + "'" +
        ", permissionId='" + getPermissionId() + "'" +
        "}";
  }
}
