package de.seven.fate.moa.jms.proccesor;

import de.seven.fate.moa.converter.MessageDTO2MessageConverter;
import de.seven.fate.moa.dto.MessageDTO;
import de.seven.fate.moa.dto.MessagesDTO;
import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.service.MessageService;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Mario on 07.05.2016.
 */
public class JmsMessagesProcessor implements JmsProcessor<MessagesDTO> {

    @Inject
    private Logger logger;

    @Inject
    private MessageService service;

    @Inject
    private MessageDTO2MessageConverter converter;

    @Override
    public void process(MessagesDTO messagesDTO) {

        List<MessageDTO> messageDTOs = messagesDTO.getMessages();

        logger.info("process " + messageDTOs.size() + " messagesDTO");

        List<Message> messages = converter.convertList(messageDTOs);

        service.saveMessages(messages);
    }

    @Override
    public String getObjectType() {

        return MessagesDTO.class.getSimpleName();
    }
}
