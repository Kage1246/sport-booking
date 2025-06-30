package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * A Role.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "role",
    uniqueConstraints = @UniqueConstraint(columnNames = {"code"})
)
public class Role extends AbstractAuditingEntity implements Serializable {

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;
}