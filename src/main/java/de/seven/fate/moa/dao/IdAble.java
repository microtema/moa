package de.seven.fate.moa.dao;

import java.io.Serializable;

/**
 * Created by Mario on 03.05.2016.
 */
public interface IdAble<I> extends Serializable {

    I getId();

    void setId(I id);
}
