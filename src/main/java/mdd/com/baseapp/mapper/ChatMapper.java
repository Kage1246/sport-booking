package mdd.com.baseapp.mapper;

import mdd.com.baseapp.domain.Chat;
import mdd.com.baseapp.dto.ChatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends AbsMapper<Chat, ChatDto, ChatDto> {
}
