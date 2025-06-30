package mdd.com.baseapp.service.direct;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mdd.com.baseapp.config.VNPAY;
import mdd.com.baseapp.config.VnpayConfig;
import mdd.com.baseapp.controller.request.VnpayReceive;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.exception.CustomException;
import mdd.com.baseapp.security.SecurityUtils;
import mdd.com.baseapp.service.base.BaseAutowire;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService extends BaseAutowire {

  private final VNPAY VNPay;
  private final TransactionService transactionService;
  private final AccountService accountService;

  @SneakyThrows
  public String getUrlNavigateVnpay(String amount) {
    String vnp_OrderInfo = "Naptien";
    String vnp_TxnRef = vnpayConfig.getRandomNumber(8);
    String vnp_IpAddr = "0.0.0.0";
    String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();
    Map vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnpayConfig.getVnp_Version());
    vnp_Params.put("vnp_Command", vnpayConfig.getVnp_Command());
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", amount);
    vnp_Params.put("vnp_CurrCode", "VND");
    //        String bank_code = null; không cần choọn ngan hang
    //        if (bank_code != null && !bank_code.isEmpty()) {
    //            vnp_Params.put("vnp_BankCode", bank_code);
    //        }
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
    vnp_Params.put("vnp_OrderType", vnpayConfig.getVnp_OrderType());
    vnp_Params.put("vnp_Locale", vnpayConfig.getVnp_Locale());

    vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    //Add Params of 2.1.0 Version
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    //Build data to hash and querystring
    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash =
        VnpayConfig.hmacSHA512(vnpayConfig.getVnp_SecretKey(), hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    transactionService.createTransaction(SecurityUtils.getCurrentUserId(),
        Double.parseDouble(amount) / 100, vnp_TxnRef);
    return vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
  }

  public String receiveVnpay(VnpayReceive receive) {
    int status = 0;
    if ("00".equals(receive.getVnp_ResponseCode())) {
      status = 1;
    }

    transactionService.updateTransaction(receive.getVnp_TxnRef(), status);
    if (status == 1) {
      return "Success";
    } else {
      return "Fail";
    }
  }

  public boolean checkBalance() {
    User user = accountService.getCurrentUser();
    if (user == null) {
      throw new CustomException("User not found");
    }
    if (user.getBalance() >= 10000f) {
      return true;
    } else {
      return false;
    }

  }
}
