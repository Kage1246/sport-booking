package mdd.com.baseapp.common.util;

import java.text.Normalizer;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class StringUtil {

  public static boolean nonText(String text) {
    return !StringUtils.hasText(text);
  }

  public static String nullIfEmpty(String text) {

    if (nonText(text)) {
      return null;
    }

    return text.trim();
  }

  public static String getCode(String text) {

    if (StringUtils.hasText(text)) {

      final String[] ss = text.split("\\|");

      return ss[0].trim();
    }

    return null;
  }

  public static String removeAccent(String input) {
    if (input == null) {
      return "";
    }
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
  }

  public static String camelToSnake(String str) {
    return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
  }

  public static String shortenString(String input, int maxLength) {
    if (input == null) {
      return "";
    }
    return input.length() > maxLength ? input.substring(0, maxLength - 3) + "..." : input;
  }
}
