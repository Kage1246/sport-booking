package mdd.com.baseapp.mapper;

import mdd.com.baseapp.domain.Facility;
import mdd.com.baseapp.dto.FacilityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacilityMapper extends AbsMapper<Facility, FacilityDto, FacilityDto> {
}
