package mdd.com.baseapp.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class TransactionE extends AbstractAuditingEntity {

  @Basic
  @Column(name = "user_id")
  private Integer userId;

  @Basic
  @Column(name = "tran_code")
  private String tranCode;

  @Basic
  @Column(name = "amount")
  private Double amount;
  @Basic
  @Column(name = "status")
  private Integer status;// 2 là đang đợi, 1 là thành cong
}
