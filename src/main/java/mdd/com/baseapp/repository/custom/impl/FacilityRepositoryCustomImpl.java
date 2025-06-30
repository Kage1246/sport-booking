package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mdd.com.baseapp.common.PageQueryBuilder;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Facility;
import mdd.com.baseapp.dto.FacilityDto;
import mdd.com.baseapp.repository.custom.FacilityRepositoryCustom;
import org.springframework.data.domain.Page;

public class FacilityRepositoryCustomImpl implements FacilityRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<Facility> page(PageFilterInput<FacilityDto> input) {
    String baseSql = "SELECT * FROM facility";
    String countSql = "SELECT COUNT(*) FROM facility";

    PageQueryBuilder<Facility> builder =
        new PageQueryBuilder<>(baseSql, countSql, Facility.class, entityManager);

    FacilityDto filter = input.getFilter();

    if (filter != null) {
      builder.addFilter("AND id = ?", filter.getId());
      builder.addLikeFilter("AND name like ?", filter.getName());
      builder.addFilter("AND phone_number = ?", filter.getPhoneNumber());
    }
    builder.addFilter("AND is_delete = ?", false);

    return builder.page(input);
  }
}
