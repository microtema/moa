package de.seven.fate.moa.builder;

import de.seven.fate.moa.dto.MessagesDTO;
import de.seven.fate.model.builder.AbstractModelBuilder;

import javax.inject.Inject;

/**
 * Created by Mario on 07.05.2016.
 */
public class MessagesDTOBuilder extends AbstractModelBuilder<MessagesDTO> {

    private final MessageDTOBuilder messageDTOBuilder;

    @Inject
    public MessagesDTOBuilder(MessageDTOBuilder messageDTOBuilder) {
        this.messageDTOBuilder = messageDTOBuilder;
    }

    @Override
    public MessagesDTO min() {

        MessagesDTO min = super.min();
        min.setMessages(messageDTOBuilder.list());

        return min;
    }
}
