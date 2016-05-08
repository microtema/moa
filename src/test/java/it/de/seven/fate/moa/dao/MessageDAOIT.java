package it.de.seven.fate.moa.dao;

import de.seven.fate.moa.dao.MessageDAO;
import de.seven.fate.moa.model.Message;
import de.seven.fate.moa.model.builder.MessageBuilder;
import de.seven.fate.model.util.CollectionUtil;
import it.de.seven.fate.moa.util.DeploymentUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Mario on 07.05.2016.
 */
@RunWith(Arquillian.class)
public class MessageDAOIT {

    @Inject
    MessageDAO sut;

    @Inject
    MessageBuilder builder;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction transaction;

    List<Message> models;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentUtil.createDeployment();
    }

    @Before
    public void setUp() throws Exception {

        models = builder.list();

        transactional(() -> sut.saveOrUpdate(models));
    }

    @After
    public void tearDown() throws Exception {

        transactional(sut::removeAll);
    }

    @Test
    public void count() {

        assertEquals(models.size(), sut.count().intValue());
    }

    @Test
    public void get() {
        Message model = CollectionUtil.random(models);
        assertEquals(model, sut.get(model));
    }

    @Test
    public void list() {
        assertEquals(models, sut.list());
    }

    @Test
    public void update() throws Exception {
        Message model = CollectionUtil.random(models);

        model.setDescription(UUID.randomUUID().toString());

        transactional(() ->
                assertEquals(model, sut.saveOrUpdate(model))
        );
    }

    @Test
    public void remove() throws Exception {
        Message model = CollectionUtil.random(models);

        transactional(() ->
                sut.remove(model)
        );

        assertNull(sut.get(model));
    }

    private void transactional(Runnable runnable) throws Exception {
        transaction.begin();
        em.joinTransaction();

        runnable.run();

        transaction.commit();
    }
}