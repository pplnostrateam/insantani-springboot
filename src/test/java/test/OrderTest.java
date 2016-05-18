package test;

import netgloo.models.Order;
import netgloo.models.OrderDao;
import netgloo.models.User;
import netgloo.models.Vegetable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by Adrian on 5/19/2016.
 */
@Transactional
public class OrderTest {

    @Autowired
    private OrderDao service;

    @After
    public void tearDown()  {
        //clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Order> list = service.getAll();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected list size", 2, list.size());

    }

    @Test
    public void testFindOne() {
        long id = new Long(1);
        Order entity = service.getById(1);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id,
                entity.getId());
    }

    @Test
    public void testFindOneNotFound() {

        Long id = Long.MAX_VALUE;

        Order entity = service.getById(id);

        Assert.assertNull("failure - expected null", entity);

    }

    @Test
    public void testCreate() {
        Order order = new  Order("Taruh barang didepan pintu");
        service.create(order);
        Order createdEntity = service.getByToken("1234");
        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null",
                createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test",
                createdEntity.getNote());

        Collection<Order> list = service.getAll();

        Assert.assertEquals("failure - expected size", 3, list.size());

    }

    @Test
    public void testUpdate() {

        Long id = new Long(1);

        Order entity = service.getById(id);

        Assert.assertNotNull("failure - expected not null", entity);

        int updatedOrderStatus = Math.max(entity.getOrderStatus()-1,0);
        entity.setOrderStatus(updatedOrderStatus);
       service.update(entity);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id,
                entity.getId());
        Assert.assertEquals("failure - expected text attribute match",
              entity  , entity.getNote());

    }

    @Test
    public void testUpdateNotFound() {

        Exception exception = null;

        Order entity = new Order();
        entity.setId(Long.MAX_VALUE);
        entity.setNote("test");

        try {
            service.update(entity);
        } catch (NoResultException e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResultException",
                exception instanceof NoResultException);

    }

    @Test
    public void testDelete() {

        Long id = new Long(1);

        Order entity = service.getById(id);

        Assert.assertNotNull("failure - expected not null", entity);

        service.delete(entity);

        Collection<Order> list = service.getAll();

        Assert.assertEquals("failure - expected size", 1, list.size());

        Order deletedEntity = service.getById(id);

        Assert.assertNull("failure - expected null", deletedEntity);

    }
}
