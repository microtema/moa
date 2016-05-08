package de.seven.fate.moa.converter;

import de.seven.fate.moa.dto.MessageDTO;
import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageDTOBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mario on 08.05.2016.
 */
public class MessageDTO2MessageConverterTest {

    MessageDTO2MessageConverter sut = new MessageDTO2MessageConverter();
    MessageDTOBuilder builder = new MessageDTOBuilder();
    MessageDTO dto = builder.random();

    @Test
    public void convertToNull() {
        assertNull(sut.convert(null));
    }

    @Test
    public void convert() {

        Message model = sut.convert(dto);
        assertNotNull(model);

        assertEquals(dto.getTitle(), model.getTitle());
        assertEquals(dto.getDescription(), model.getDescription());
        assertEquals(dto.getImage(), model.getImage());
    }

}