package mdd.com.baseapp.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {
  @Bean
  public ModelMapper modelMapper() {
    // Tạo object và cấu hình
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    return modelMapper;
  }

  @Bean
  public JavaTimeModule javaTimeModule() {
    JavaTimeModule module = new JavaTimeModule();

    // Format cho LocalTime
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(Constant.Format.Time.TIME_SHORT);
    module.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
    module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

    // Format cho LocalDateTime
    DateTimeFormatter dateTimeFormatter =
        DateTimeFormatter.ofPattern(Constant.Format.Time.FULL_DATE_TIME);
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

    return module;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(javaTimeModule());
    //        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    return objectMapper;
  }
}
