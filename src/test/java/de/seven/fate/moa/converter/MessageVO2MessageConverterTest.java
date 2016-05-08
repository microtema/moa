package de.seven.fate.moa.converter;

import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageVOBuilder;
import de.seven.fate.moa.vo.MessageVO;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mario on 08.05.2016.
 */
public class MessageVO2MessageConverterTest {

    MessageVO2MessageConverter sut = new MessageVO2MessageConverter();
    MessageVOBuilder builder = new MessageVOBuilder();
    MessageVO vo = builder.random();

    @Test
    public void convertToNull() {

        assertNull(sut.convert(null));
    }

    @Test
    public void convert() {

        Message model = sut.convert(vo);
        assertNotNull(model);

        assertEquals(vo.getTitle(), model.getTitle());
        assertEquals(vo.getDescription(), model.getDescription());
        assertEquals(vo.getImage(), model.getImage());
    }
}