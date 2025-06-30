package mdd.com.baseapp.mapper;

import mdd.com.baseapp.domain.FaTimeConfig;
import mdd.com.baseapp.dto.FaTimeConfigDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FaTimeConfigMapper
    extends AbsMapper<FaTimeConfig, FaTimeConfigDto, FaTimeConfigDto> {
}
