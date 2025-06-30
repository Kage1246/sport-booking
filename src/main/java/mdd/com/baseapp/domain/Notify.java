package mdd.com.baseapp.domain;

import jakarta.persistence.Basic;
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
public class Notify extends AbstractAuditingEntity {
  @Basic
  @Column(name = "user_id")
  private Integer userId;

  @Basic
  @Column(name = "title")
  private String title;

  @Basic
  @Column(name = "body")
  private String body;

  @Basic
  @Column(name = "type")
  private Integer type;

  @Basic
  @Column(name = "ref_id")
  private Integer refId;
}
