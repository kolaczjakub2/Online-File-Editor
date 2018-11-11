package st.pk.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class FileInfoGridDTO {
    private Date createdDate;
    private String createdBy;
    private String modifiedBy;
    private Date modifiedDate;
    private Long version;
    private String name;
    private String size;
    private String description;
    private UUID id;
    private Boolean isEditing;
    private String content;
    private String editedBy;
}
