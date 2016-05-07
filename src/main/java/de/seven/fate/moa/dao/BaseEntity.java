package de.seven.fate.moa.dao;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mario on 05.04.2016.
 */
@MappedSuperclass
@EntityListeners(TimestampListener.class)
public class BaseEntity<I extends Serializable> implements IdAble<I> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private I id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Version
    private Long version;

    @Override
    public I getId() {
        return id;
    }

    @Override
    public void setId(I id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
