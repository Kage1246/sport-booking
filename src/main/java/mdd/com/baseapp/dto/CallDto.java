package mdd.com.baseapp.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CallDto implements Serializable {
  Integer id;
  Object data;
}
