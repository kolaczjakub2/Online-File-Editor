package st.pk.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class FullFileInfoDTO {
    private UUID id;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private Long version;
    private String name;
    private String description;
    private String content;
    private String size;
}
