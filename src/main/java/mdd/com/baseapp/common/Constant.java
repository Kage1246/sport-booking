package mdd.com.baseapp.common;

public interface Constant {
  int PAGE_SIZE = 10;

  int PAGE_NUM = 0;

  int STATUS_ACTIVE = 1;

  int STATUS_INACTIVE = 0;

  String HTTP_OK = "200";

  interface Format {
    interface Time {
      String FULL_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
      String DATE = "dd/MM/yyyy";
      String TIME_SHORT = "HH:mm";
    }
  }

  interface Permission {
    String ADMIN_ONLY = "ADMIN_ONLY";
    String MANAGER_ONLY = "MANAGER_ONLY";
    String CUSTOMER_ONLY = "CUSTOMER_ONLY";
    String ADMIN = "ADMIN";
    String MANAGER = "MANAGER";
    String CUSTOMER = "CUSTOMER";
  }

  interface Response {
    interface Message {
      String OK = "Thực hiện thành công!";
    }
  }
}
