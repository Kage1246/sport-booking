package mdd.com.baseapp.service.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
public class UtilBeanAutowire {
  @Autowired
  public ModelMapper modelMapper;

  @Autowired
  public ObjectMapper objectMapper;

  @Autowired
  public Validator validator;
}
