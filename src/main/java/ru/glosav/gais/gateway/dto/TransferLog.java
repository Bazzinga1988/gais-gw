package ru.glosav.gais.gateway.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

@Data
@ToString
@Entity
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic(optional = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts;
    @NotNull
    private String sessionId;
    @NotNull
    private Type type;
    @NotNull
    private long objId;
    @NotNull
    private String extId;

    public enum Type {
        COMPANY,
        TRANSPORT_UNIT
    }

}
