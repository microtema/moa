package de.seven.fate.moa.dao;

import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageBuilder;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Mario on 08.05.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class TimestampListenerTest {

    @InjectMocks
    TimestampListener sut;

    MessageBuilder builder = new MessageBuilder();
    Message model = builder.random();

    @Test
    public void prePersist() throws Exception {
        sut.prePersist(model);
        assertNotNull(model.getCreatedDate());
    }

    @Test
    public void preUpdate() throws Exception {
        sut.preUpdate(model);
        assertNotNull(model.getUpdateDate());
    }

}