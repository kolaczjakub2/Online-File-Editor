package st.pk.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreatedFileInfoDTO {
    private String name;
    private String description;
    private String content;
    private Long version;
}
