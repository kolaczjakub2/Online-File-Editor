package st.pk.demo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Message implements Serializable {
    private String message;
    private String from;
    private String to;
    private String time;
}
