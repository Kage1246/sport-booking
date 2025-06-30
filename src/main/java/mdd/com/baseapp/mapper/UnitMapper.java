package mdd.com.baseapp.mapper;

import mdd.com.baseapp.domain.Unit;
import mdd.com.baseapp.dto.UnitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper extends AbsMapper<Unit, UnitDto, UnitDto> {

}
