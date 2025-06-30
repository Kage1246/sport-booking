package mdd.com.baseapp.mapper;

import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Notify;
import mdd.com.baseapp.dto.NotifyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotifyMapper extends AbsMapper<Notify, CommonRq, NotifyDto> {
}

