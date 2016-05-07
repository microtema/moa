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

    public void saveMessages(List<Message> messages) {

        dao.saveOrUpdate(messages);
    }
}
