package mdd.com.baseapp.config;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class VnpayConfig {
  @Value("${app.payment.vnpay.vnp_TmnCode}")
  private String vnp_TmnCode;

  @Value("${app.payment.vnpay.vnp_SecretKey}")
  private String vnp_SecretKey;
  @Value("${app.payment.vnpay.vnp_Version}")
  private String vnp_Version;
  @Value("${app.payment.vnpay.vnp_Command}")
  private String vnp_Command;
  @Value("${app.payment.vnpay.vnp_CurrCode}")
  private String vnp_CurrCode;
  @Value("${app.payment.vnpay.vnp_Locale}")
  private String vnp_Locale;
  @Value("${app.payment.vnpay.vnp_OrderType}")
  private String vnp_OrderType;
  @Value("${app.payment.vnpay.vnp_ReturnUrl}")
  private String vnp_ReturnUrl;
  @Value("${app.payment.vnpay.vnp_PayUrl}")
  private String vnp_PayUrl;

  public static String getRandomNumber(int len) {
    Random rnd = new Random();
    String chars = "0123456789";
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return sb.toString();
  }

  public static String hmacSHA512(final String key, final String data) {
    try {

      if (key == null || data == null) {
        throw new NullPointerException();
      }
      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes();
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();

    } catch (Exception ex) {
      return "";
    }
  }

}
