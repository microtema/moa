package de.seven.fate.moa.facade;

import de.seven.fate.moa.converter.Message2MessageVOConverter;
import de.seven.fate.moa.converter.MessageVO2MessageConverter;
import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.service.MessageService;
import de.seven.fate.moa.vo.MessageVO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mario on 08.05.2016.
 */
@Stateless
public class MessageFacade {

    @Inject
    private Logger logger;

    @Inject
    private MessageService service;

    @Inject
    private MessageVO2MessageConverter messageVO2MessageConverter;

    @Inject
    private Message2MessageVOConverter message2MessageVOConverter;


    public List<MessageVO> findAllOrderedByTitle() {
        logger.log(Level.SEVERE, "find all Messages ordered by title");

        return message2MessageVOConverter.convertList(service.findAllOrderedByTitle());
    }

    public MessageVO findById(Long messageId) {
        logger.log(Level.SEVERE, "find Message by id");

        return message2MessageVOConverter.convert(service.findById(messageId));
    }

    public MessageVO updateMessage(MessageVO messageVO) {
        logger.log(Level.SEVERE, "update Message: " + messageVO);

        Message message = messageVO2MessageConverter.convert(messageVO);

        return message2MessageVOConverter.convert(service.updateMessage(message));
    }

    public MessageVO saveMessage(MessageVO messageVO) {
        logger.log(Level.SEVERE, "save Message: " + messageVO);

        Message message = messageVO2MessageConverter.convert(messageVO);

        service.saveMessage(message);

        return message2MessageVOConverter.convert(message);
    }

    public Boolean deleteMessage(Long messageId) {
        logger.log(Level.SEVERE, "delete message by id: " + messageId);

        service.deleteMessage(messageId);

        return Boolean.TRUE;
    }
}
