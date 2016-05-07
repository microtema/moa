package de.seven.fate.moa.dao;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Mario on 05.04.2016.
 */
public class TimestampListener {

    @PrePersist
    void prePersist(Object object) {

        if(object instanceof BaseEntity){
            ((BaseEntity)object).setCreatedDate(new Date());
        }
    }

    @PreUpdate
    void preUpdate(Object object) {
        if(object instanceof BaseEntity){
            ((BaseEntity)object).setUpdateDate(new Date());
        }
    }
}
