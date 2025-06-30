package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mdd.com.baseapp.common.PageQueryBuilder;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.dto.UnitDto;
import mdd.com.baseapp.repository.custom.UnitRepositoryCustom;
import org.springframework.data.domain.Page;

public class UnitRepositoryCustomImpl implements UnitRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<Unit> page(PageFilterInput<UnitDto> input) {
    String baseSql = "SELECT * FROM unit";
    String countSql = "SELECT COUNT(*) FROM unit";

    PageQueryBuilder<Unit> builder =
        new PageQueryBuilder<>(baseSql, countSql, Unit.class, entityManager);

    UnitDto filter = input.getFilter();

    if (filter != null) {
      builder.addFilter("AND id = ?", filter.getId());
      builder.addFilter("AND facility = ?", filter.getFacilityId());
      builder.addFilter("AND price >= ? ", filter.getMinPrice());
      builder.addFilter("AND price <= ? ", filter.getMaxPrice());
      builder.addLikeFilter("AND name like ?", filter.getName());
    }
    builder.addFilter("AND is_delete = ?", false);

    return builder.page(input);
  }
}
