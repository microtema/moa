package de.seven.fate.moa.converter;

import de.seven.fate.moa.dto.MessageDTO;
import de.seven.fate.moa.model.builder.MessageVOBuilder;
import de.seven.fate.moa.vo.MessageVO;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mario on 11.05.2016.
 */
public class MessageVO2MessageDTOConverterTest {

    MessageVO2MessageDTOConverter sut = new MessageVO2MessageDTOConverter();
    MessageVOBuilder builder = new MessageVOBuilder();
    MessageVO vo = builder.random();

    @Test
    public void convertToNull() {
        assertNull(sut.convert(null));
    }

    @Test
    public void convert() {

        MessageDTO model = sut.convert(vo);
        assertNotNull(model);

        assertEquals(vo.getTitle(), model.getTitle());
        assertEquals(vo.getDescription(), model.getDescription());
        assertEquals(vo.getImage(), model.getImage());
    }
}