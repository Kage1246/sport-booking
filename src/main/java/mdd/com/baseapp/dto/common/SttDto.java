/**
 * @author quangnv created on 3/25/2020
 */

package mdd.com.baseapp.dto.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SttDto implements Serializable {

  private long stt = 1;

  private Boolean isActive = Boolean.FALSE;

}
