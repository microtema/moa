package de.seven.fate.moa.converter;

import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageBuilder;
import de.seven.fate.moa.vo.MessageVO;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mario on 08.05.2016.
 */
public class Message2MessageVOConverterTest {

    Message2MessageVOConverter sut = new Message2MessageVOConverter();
    MessageBuilder builder = new MessageBuilder();
    Message model = builder.random();

    @Test
    public void convertToNull() {

        assertNull(sut.convert(null));
    }

    @Test
    public void convert() {

        MessageVO vo = sut.convert(this.model);
        assertNotNull(vo);

        assertEquals(this.model.getTitle(), vo.getTitle());
        assertEquals(this.model.getDescription(), vo.getDescription());
        assertEquals(this.model.getImage(), vo.getImage());
    }
}