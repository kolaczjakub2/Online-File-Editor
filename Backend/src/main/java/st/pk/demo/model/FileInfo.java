package st.pk.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class FileInfo {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    public UUID id;

    @CreatedBy
    String createdBy;

    @CreatedDate
    Long createdDate;

    @LastModifiedBy
    String modifiedBy;

    @LastModifiedDate
    Long modifiedDate;

    @Version
    Long version;

    String name;

    String description;
}
