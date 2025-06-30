package mdd.com.baseapp.mapper;

import mdd.com.baseapp.domain.FaTimeConfigDetail;
import mdd.com.baseapp.dto.FaTimeConfigDetailDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FaTimeConfigDetailMapper
    extends AbsMapper<FaTimeConfigDetail, FaTimeConfigDetailDto, FaTimeConfigDetailDto> {
}
