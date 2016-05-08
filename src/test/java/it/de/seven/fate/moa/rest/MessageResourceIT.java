package it.de.seven.fate.moa.rest;

import de.seven.fate.moa.dao.MessageDAO;
import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageBuilder;
import de.seven.fate.moa.model.builder.MessageVOBuilder;
import de.seven.fate.moa.rest.MessageResource;
import de.seven.fate.moa.vo.MessageVO;
import it.de.seven.fate.moa.util.DeploymentUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mario on 08.05.2016.
 */
@RunWith(Arquillian.class)
public class MessageResourceIT {

    @Inject
    MessageResource sut; // System under Test

    @Inject
    MessageDAO dao;

    @Inject
    MessageBuilder builder;

    @Inject
    MessageVOBuilder voBuilder;

    @Inject
    UserTransaction transaction;

    @PersistenceContext
    EntityManager em;

    List<Message> models;


    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentUtil.createDeployment();
    }

    @Test
    @InSequence(1)
    public void setUp() throws Exception {

        models = builder.list();
        transactional(() -> dao.save(models));
    }

    @Test
    @InSequence(100)
    public void tearDown() throws Exception {

        transactional(dao::removeAll);
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void testFindAllOrderedByName(@ArquillianResource URL baseURL) throws Exception {

        // given

        // when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse<List> clientResponse = request.get(List.class);

        // then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        List messages = clientResponse.getEntity();

        assertNotNull(messages);
        assertFalse(messages.isEmpty());
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void findById(@ArquillianResource URL baseURL) throws Exception {
        Long id = 1l;
        // given

        // when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message/" + id).toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("GET");
        ClientResponse<MessageVO> clientResponse = request.get(MessageVO.class);

        // then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        MessageVO message = clientResponse.getEntity();

        assertNotNull(message);
        assertEquals(id, message.getId());
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void updateMember(@ArquillianResource URL baseURL) throws Exception {

        voBuilder = new MessageVOBuilder(); //there is not CDI container here
        MessageVO vo = voBuilder.min();
        vo.setId(1l);
        // given

        // when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("PUT");
        request.body(MediaType.APPLICATION_JSON, vo);
        ClientResponse<MessageVO> clientResponse = request.put(MessageVO.class);

        // then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        MessageVO message = clientResponse.getEntity();

        assertNotNull(message);
        assertEquals(vo, message);
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void saveMember(@ArquillianResource URL baseURL) throws Exception {
        voBuilder = new MessageVOBuilder(); //there is not CDI container here
        MessageVO vo = voBuilder.min();
        vo.setId(1l);
        // given

        // when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message").toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("POST");
        request.body(MediaType.APPLICATION_JSON, vo);
        ClientResponse<MessageVO> clientResponse = request.post(MessageVO.class);

        // then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        MessageVO message = clientResponse.getEntity();

        assertNotNull(message);
        assertNotNull(message.getId());
    }

    @Test
    @RunAsClient
    @InSequence(3)
    public void deleteMember(@ArquillianResource URL baseURL) throws Exception {
        Long id = 1l;
        // given

        // when
        ClientRequest request = new ClientRequest(new URL(baseURL, "rest/message/" + id).toExternalForm());
        request.accept(MediaType.APPLICATION_JSON);
        request.setHttpMethod("DELETE");
        ClientResponse<Boolean> clientResponse = request.delete(Boolean.class);

        // then
        assertEquals(Response.Status.OK.getStatusCode(), clientResponse.getStatus());

        assertTrue(clientResponse.getEntity());
    }

    private void transactional(Runnable runnable) throws Exception {
        transaction.begin();
        em.joinTransaction();

        runnable.run();

        transaction.commit();
    }

}