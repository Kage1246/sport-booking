package mdd.com.baseapp.common.constant;

/**
 * Danh sách các mã HTTP status code thông dụng dùng trong hệ thống.
 */
public interface HttpStatusCode {

  // ✅ 2xx – Thành công
  public static final Integer OK = 200; // Yêu cầu thành công (GET, PUT, DELETE)
  public static final Integer CREATED = 201; // Tạo mới thành công (POST)
  public static final Integer ACCEPTED = 202; // Đã nhận yêu cầu, xử lý sau (xử lý async)
  public static final Integer NO_CONTENT = 204;
  // Thành công, không có nội dung trả về (DELETE, PUT)

  // ❌ 4xx – Lỗi từ phía client
  public static final Integer BAD_REQUEST = 400; // Yêu cầu sai định dạng, thiếu tham số
  public static final Integer UNAUTHORIZED = 401; // Chưa xác thực (thiếu token)
  public static final Integer FORBIDDEN = 403; // Không có quyền truy cập
  public static final Integer NOT_FOUND = 404; // Không tìm thấy tài nguyên
  public static final Integer METHOD_NOT_ALLOWED = 405; // Method không được hỗ trợ
  public static final Integer CONFLICT = 409; // Xung đột dữ liệu (vd: ID đã tồn tại)
  public static final Integer UNPROCESSABLE_ENTITY = 422; // Dữ liệu không hợp lệ (validate logic)

  // 🔥 5xx – Lỗi từ phía server
  public static final Integer INTERNAL_SERVER_ERROR = 500; // Lỗi không xác định (exception runtime)
  public static final Integer BAD_GATEWAY = 502; // Gateway lỗi (vd: Nginx <-> backend)
  public static final Integer SERVICE_UNAVAILABLE = 503; // Dịch vụ tạm thời không sẵn sàng
  public static final Integer GATEWAY_TIMEOUT = 504; // Gateway timeout
}
