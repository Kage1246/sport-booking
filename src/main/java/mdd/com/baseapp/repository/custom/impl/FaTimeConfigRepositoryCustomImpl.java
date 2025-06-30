package mdd.com.baseapp.repository.custom.impl;

import jakarta.persistence.EntityManager;
import mdd.com.baseapp.common.PageQueryBuilder;
import mdd.com.baseapp.controller.request.PageFilterInput;
import mdd.com.baseapp.domain.FaTimeConfig;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import mdd.com.baseapp.repository.custom.FaTimeConfigRepositoryCustom;
import org.springframework.data.domain.Page;

public class FaTimeConfigRepositoryCustomImpl implements FaTimeConfigRepositoryCustom {
  private final EntityManager entityManager;

  public FaTimeConfigRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Page<FaTimeConfig> page(PageFilterInput<FaTimeConfigDto> pageFilterInput) {
    String baseSql = "SELECT * FROM fa_time_config";
    String countSql = "SELECT COUNT(*) FROM fa_time_config";

    PageQueryBuilder<FaTimeConfig> builder =
        new PageQueryBuilder<>(baseSql, countSql, FaTimeConfig.class, entityManager);

    if (pageFilterInput != null) {
      builder.addLikeFilter("AND name like ?", pageFilterInput.getFilter().getName());
      builder.addFilter("AND description like ?", pageFilterInput.getFilter().getDescription());
      builder.addFilter("AND period = ?", pageFilterInput.getFilter().getPeriod());
      builder.addFilter("AND id = ?", pageFilterInput.getFilter().getId());
      builder.addFilter("AND is_delete = ?", false);
    }
    return builder.page(pageFilterInput);
  }
}
