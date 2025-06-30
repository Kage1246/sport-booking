package mdd.com.baseapp.common.enumpackage;

public enum StatusSlotEnum {
  NOT_AVAILABLE,  // không khả dụng
  AVAILABLE,  // trống
  WAITING,  //giữ chỗ cho người  đã đặt theo lịch cố định
  IN_PROGRESS, // có người đang đặt, chờ thanh toán
  SUCCESS, // Đã đặt
}
