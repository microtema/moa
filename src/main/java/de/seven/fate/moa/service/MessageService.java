package de.seven.fate.moa.service;

import de.seven.fate.moa.dao.MessageDAO;
import de.seven.fate.moa.model.Message;
import org.apache.commons.lang3.Validate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Mario on 07.05.2016.
 */
@Stateless
public class MessageService {

    @Inject
    private Logger logger;

    @Inject
    private MessageDAO dao;


    public List<Message> findAllOrderedByTitle() {

        return dao.list();
    }

    public Message findById(Long messageId) {
        notNull(messageId);

        return dao.get(messageId);
    }

    public Message updateMessage(Message message) {
        notNull(message);

        return dao.saveOrUpdate(message);
    }

    public Message saveMessage(Message message) {
        notNull(message);

        logger.info("save message: " + message);

        dao.save(message);

        return message;
    }

    public Boolean deleteMessage(Long messageId) {
        notNull(messageId);

        logger.info("remove message with Id: " + messageId);

        dao.remove(messageId);

        return Boolean.TRUE;
    }

    public void saveMessages(List<Message> messages) {
        logger.info("save " + messages.size() + " messages");

        dao.saveOrUpdate(messages);
    }
}
