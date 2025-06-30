package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mdd.com.baseapp.common.enumpackage.ConfigTypeEnum;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "system_config",
    indexes = {@Index(name = "idx_system_config_key", columnList = "key")}
)
public class SystemConfig extends AbstractAuditingEntity implements Serializable {
  @Column(nullable = false, unique = true)
  private String key;
  @Column
  @Enumerated(EnumType.STRING)
  private ConfigTypeEnum type;
  @Column
  private String description;
  @Column
  private Long longValue;
  @Column
  private Double doubleValue;
  @Column
  private String textValue;
  @Column
  private Boolean booleanValue;
}
