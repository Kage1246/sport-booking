package mdd.com.baseapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Otp extends AbstractAuditingEntity {
  @Column(name = "code")
  private String code;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "type")
  private Integer type;

  @Column(name = "status")
  private Boolean status;

  public Otp(String code, Integer userId, Integer type) {
    this.code = code;
    this.userId = userId;
    this.type = type;
    this.status = true;
  }
}
