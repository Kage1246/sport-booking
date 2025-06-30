package mdd.com.baseapp.common.constant;

public interface HttpStatusMessage {

  // ✅ 2xx – Thành công
  String OK = "Thực hiện thành công";
  String CREATED = "Tạo mới thành công";
  String ACCEPTED = "Yêu cầu đã được chấp nhận";
  String NO_CONTENT = "Không có nội dung trả về";

  // ❌ 4xx – Lỗi từ phía client
  String BAD_REQUEST = "Yêu cầu không hợp lệ";
  String UNAUTHORIZED = "Chưa xác thực, vui lòng đăng nhập";
  String FORBIDDEN = "Bạn không có quyền truy cập chức năng/ tài nguyên này";
  String NOT_FOUND = "Không tìm thấy tài nguyên yêu cầu";
  String METHOD_NOT_ALLOWED = "Phương thức không được hỗ trợ";
  String CONFLICT = "Xung đột dữ liệu";
  String UNPROCESSABLE_ENTITY = "Dữ liệu không hợp lệ";

  // 🔥 5xx – Lỗi từ phía server
  String INTERNAL_SERVER_ERROR = "Lỗi máy chủ";
  String BAD_GATEWAY = "Lỗi gateway";
  String SERVICE_UNAVAILABLE = "Dịch vụ hiện không khả dụng";
  String GATEWAY_TIMEOUT = "Gateway không phản hồi";
}
