package mdd.com.baseapp.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mdd.com.baseapp.common.util.StringUtil;
import mdd.com.baseapp.controller.request.PageFilterInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageQueryBuilder<T> {

  private final String baseSql;
  private final String countSql;
  private final Class<T> resultClass;
  private final EntityManager em;
  private final List<Object> parameters = new ArrayList<>();
  private final StringBuilder whereClause = new StringBuilder(" WHERE 1=1 ");

  public PageQueryBuilder(String baseSql, String countSql, Class<T> resultClass, EntityManager em) {
    this.baseSql = baseSql;
    this.countSql = countSql;
    this.resultClass = resultClass;
    this.em = em;
  }

  /**
   * @param obj Nhận vào 1 class và 1 Map. Với mỗi trường có giá trị của map thì thêm filter vào class.
   */
  public void addFilter(Object obj, Map<String, String> fieldOperators) {
    if (obj == null || fieldOperators == null) {
      return;
    }

    Class<?> clazz = obj.getClass();

    for (Map.Entry<String, String> entry : fieldOperators.entrySet()) {
      String fieldName = entry.getKey();
      String operator = entry.getValue(); // e.g. "=", "like", ">=", "<"

      try {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(obj);

        if (value == null) {
          continue;
        }
        if (value instanceof String str && str.trim().isEmpty()) {
          continue;
        }

        String column = StringUtil.camelToSnake(fieldName);

        switch (operator.toLowerCase()) {
          case "like":
          case ">":
          case "<":
          case ">=":
          case "<=":
          case "=":
          case "!=":
            whereClause.append(" AND ").append(column).append(" ").append(operator).append(" ?");
            parameters.add(value);
            break;
          default:
            throw new IllegalArgumentException("Toán tử không hỗ trợ: " + operator);
        }
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(
            "Lỗi khi truy cập field '" + fieldName + "' trong " + clazz.getSimpleName(), e);
      }
    }
  }


  /**
   * @param obj Nhận vào 1 class. Với mỗi trường có giá trị của class thì thêm filer tương ứng.
   */
  public void addFilter(Object obj) {
    if (obj == null) {
      return;
    }

    Class<?> clazz = obj.getClass();
    Field[] fields = clazz.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true); // Cho phép truy cập cả private field

      try {
        Object value = field.get(obj);
        if (value != null && !(value instanceof String && ((String) value).trim().isEmpty())) {
          String column = StringUtil.camelToSnake(field.getName());
          whereClause.append(" AND ").append(column).append(" = ?");
          parameters.add(value);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Không đọc được field " + field.getName(), e);
      }
    }
  }

  /**
   * @param condition
   * @param value     Hàm thêm filter. Không chấp nhận rỗng
   */
  public void addFilter(String condition, Object value) {
    if (value != null && !value.toString().trim().isEmpty()) {
      whereClause.append(" ").append(condition).append(" ");
      parameters.add(value);
    }
  }

  /**
   * @param condition
   * @param value     Hàm thêm filter. Chấp nhận cả rỗng
   */
  public void addLikeFilter(String condition, Object value) {
    if (value != null) {
      whereClause.append(" ").append(condition).append(" ");
      parameters.add("%" + value + "%");
    }
  }

  public List<T> getResult(PageFilterInput<?> input) {
    StringBuilder finalSql = new StringBuilder(baseSql).append(whereClause);
    applySorting(finalSql, input);

    Query query = em.createNativeQuery(finalSql.toString(), resultClass);
    setParams(query);

    applyPaging(query, input);

    return query.getResultList();
  }

  public long getCount() {
    Query query = em.createNativeQuery(countSql + whereClause);
    setParams(query);
    Object result = query.getSingleResult();
    return ((Number) result).longValue();
  }

  private void setParams(Query query) {
    for (int i = 0; i < parameters.size(); i++) {
      query.setParameter(i + 1, parameters.get(i));
    }
  }

  private void applySorting(StringBuilder sql, PageFilterInput<?> input) {
    if (input.getSortProperty() != null) {
      sql.append(" ORDER BY ").append(input.getSortProperty());
      if ("desc".equalsIgnoreCase(input.getSortOrder())) {
        sql.append(" DESC");
      } else {
        sql.append(" ASC");
      }
    }
  }

  private void applyPaging(Query query, PageFilterInput<?> input) {
    if (input.getPageNumber() != null && input.getPageSize() != null) {
      int start = (input.getPageNumber() - 1) * input.getPageSize();
      query.setFirstResult(start);
      query.setMaxResults(input.getPageSize());
    }
  }

  public Page<T> page(PageFilterInput<?> input) {
    List<T> data = getResult(input);
    long total = getCount();
    Pageable pageable = PageRequest.of(
        input.getPageNumber() != null ? input.getPageNumber() - 1 : 0,
        input.getPageSize() != null ? input.getPageSize() : 20
    );
    return new PageImpl<>(data, pageable, total);
  }
}
