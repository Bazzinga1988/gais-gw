package ru.glosav.gais.gateway.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.annotation.Resource;
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
    @Column
    private String extId;
    @NotNull
    private Result result;
    @Column
    private String msg;

    public enum Type {
        COMPANY,
        TRANSPORT_UNIT
    }

    public enum Result {
        SUCCESS,
        ERROR
    }

}
