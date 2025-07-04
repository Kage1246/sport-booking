package mdd.com.baseapp.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("checkstyle:MemberName")
public class VnpayReceive {
  private String vnp_Amount;
  private String vnp_BankCode;
  private String vnp_BankTranNo;
  private String vnp_CardType;
  private String vnp_OrderInfo;
  private String vnp_PayDate;
  private String vnp_ResponseCode;
  private String vnp_SecureHash;
  private String vnp_TmnCode;
  private String vnp_TransactionNo;
  private String vnp_TransactionStatus;
  private String vnp_TxnRef;
}
