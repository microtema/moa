package de.seven.fate.moa.service;

import de.seven.fate.moa.dao.MessageDAO;
import de.seven.fate.moa.model.Message;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Mario on 07.05.2016.
 */
@Stateless
public class MessageService {

    @Inject
    private MessageDAO dao;


    public List<Message> findAllOrderedByTitle() {

        return dao.list();
    }

    public Message findById(Long messageId) {

        return dao.get(messageId);
    }

    public Message updateMessage(Message message) {

        return dao.saveOrUpdate(message);
    }

    public Message saveMessage(Message message) {

        dao.save(message);

        return message;
    }

    public Boolean deleteMessage(Long messageId) {

        dao.remove(messageId);

        return Boolean.TRUE;
    }

    public void saveMessages(List<Message> messages) {

        dao.saveOrUpdate(messages);
    }
}
