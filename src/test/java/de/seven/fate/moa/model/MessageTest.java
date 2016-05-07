package de.seven.fate.moa.model;

import de.seven.fate.moa.dao.BaseEntity;
import de.seven.fate.moa.model.builder.MessageBuilder;
import junit.framework.Assert;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import javax.persistence.Entity;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Mario on 07.05.2016.
 */
public class MessageTest {

    Message sut;

    MessageBuilder builder = new MessageBuilder();

    @Test
    public void testImplementation() {
        assertTrue(BaseEntity.class.isAssignableFrom(Message.class));
    }

    @Test
    public void testAnnotation() {
        assertNotNull(Message.class.getAnnotation(Entity.class));
    }

    @Test
    public void testEquals() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setId(null);
        sut.setCreatedDate(null);
        sut.setUpdateDate(null);
        sut.setVersion(null);

        //then
        assertEquals(sut, clone);
    }

    @Test
    public void testHash() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setId(null);
        sut.setCreatedDate(null);
        sut.setUpdateDate(null);
        sut.setVersion(null);

        //then
        assertEquals(sut.hashCode(), clone.hashCode());
    }

    @Test
    public void testNotEqualsOnDifferentTitle() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setTitle(null);

        //then
        assertFalse(sut.equals( clone));
    }

    @Test
    public void testNotHashOnDifferentTitle() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setTitle(UUID.randomUUID().toString());

        //then
        assertTrue(sut.hashCode() != clone.hashCode());
    }

    @Test
    public void testNotEqualsOnDifferentDescription() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setDescription(UUID.randomUUID().toString());

        //then
        assertFalse(sut.equals( clone));
    }

    @Test
    public void testNotHashOnDifferentDescription() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setDescription(UUID.randomUUID().toString());

        //then
        assertTrue(sut.hashCode() != clone.hashCode());
    }

    @Test
    public void testNotEqualsOnDifferentImage() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setImage(UUID.randomUUID().toString());

        //then
        assertFalse(sut.equals( clone));
    }

    @Test
    public void testNotHashOnDifferentImage() {

        //given
        sut = builder.min();
        Message clone = SerializationUtils.clone(sut);

        //when
        sut.setImage(UUID.randomUUID().toString());

        //then
        assertTrue(sut.hashCode() != clone.hashCode());
    }
}