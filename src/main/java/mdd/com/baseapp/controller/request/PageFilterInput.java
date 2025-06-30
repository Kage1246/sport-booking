package mdd.com.baseapp.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageFilterInput<T> {
  private Integer pageNumber;
  private Integer pageSize;
  private T filter;
  private String common;
  private String sortProperty;
  private String sortOrder;

  public Integer getPageNumber() {
    return (pageNumber == null || pageNumber < 1) ? 1 : pageNumber;
  }

  public Integer getPageSize() {
    return (pageSize == null || pageSize < 1) ? 10 : pageSize;
  }
}
