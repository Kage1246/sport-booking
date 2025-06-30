package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * An authority (a security role) used by Spring Security.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "Permission",
    uniqueConstraints = @UniqueConstraint(columnNames = {"code"})
)
public class Permission extends AbstractAuditingEntity implements Serializable {

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
