package mz.org.fgh.mentoring.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public abstract class GenericEntity implements Serializable {

    private Long id;

    private String code;

    private String uuid;

    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
