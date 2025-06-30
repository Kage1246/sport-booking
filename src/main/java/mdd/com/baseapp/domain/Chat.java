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
public class Chat extends AbstractAuditingEntity {

  @Basic
  @Column(name = "from_user_id")
  private Integer fromUserId;
  @Basic
  @Column(name = "from_user_name")
  private String fromUserName;
  @Basic
  @Column(name = "to_user_id")
  private Integer toUserId;
  @Basic
  @Column(name = "to_user_name")
  private String toUserName;
  @Basic
  @Column(name = "content")
  private String content;
  @Basic
  @Column(name = "type")
  private Integer type;
}
