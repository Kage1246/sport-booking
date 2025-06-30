package mdd.com.baseapp.service.direct;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import mdd.com.baseapp.controller.request.CommonRq;
import mdd.com.baseapp.domain.Chat;
import mdd.com.baseapp.domain.User;
import mdd.com.baseapp.dto.ChatDto;
import mdd.com.baseapp.dto.base.PageDto;
import mdd.com.baseapp.mapper.ChatMapper;
import mdd.com.baseapp.repository.ChatRepository;
import mdd.com.baseapp.security.SecurityUtils;
import mdd.com.baseapp.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends CrudService<Chat, Integer, CommonRq, ChatDto, ChatDto> {

  public ChatService(ChatRepository repository, ChatMapper mapper) {
    super(repository, mapper);
  }

  @Override
  public void beforeSave(ChatDto request, Chat chat) {
    List<User> list =
        userRepository.findAllById(Arrays.asList(request.getFromUserId(), request.getToUserId()));
    for (User u : list) {
      if (Objects.equals(u.getId(), request.getFromUserId())) {
        request.setFromUserName(u.getUsername());
      }

      if (Objects.equals(u.getId(), request.getToUserId())) {
        request.setToUserName(u.getUsername());
      }
    }
  }

  @Override
  public PageDto page(CommonRq params) {
    params.setUserId(SecurityUtils.getCurrentUserId());
    final Page<Chat> page =
        chatRepository.page(getPageable(params), params);

    return PageDto.builder()
        .contents(page.map(this::toResponse).getContent())
        .total(page.getTotalElements())
        .build();
  }
}
