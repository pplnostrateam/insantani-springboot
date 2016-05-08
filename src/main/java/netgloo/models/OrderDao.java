package netgloo.models;

import java.util.List;
import netgloo.models.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the User entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO wihtout any XML configuration and also provide the Spring 
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */

@Repository
@Transactional
public class OrderDao {
	 // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * Save the user in the database.
   */
  public void create(Order user) {
    System.out.println("LETS PERSIST");
    System.out.println(user.getToken());
    System.out.println(user.getCreated());
    entityManager.persist(user);
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public void delete(Order user) {
    if (entityManager.contains(user))
      entityManager.remove(user);
    else
      entityManager.remove(entityManager.merge(user));
    return;
  }
  
  /**
   * Return all the users stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List<Order> getAll() {
    return entityManager.createQuery("from Order").getResultList();
  }
  
  /**
   * Return the user having the passed email.
   */
  public Order getByToken(String name) {
    return (Order) entityManager.createQuery(
        "from Order where token like :name")
        .setParameter("name", name)
        .getSingleResult();
  }

  /**
   * Return the user having the passed id.
   */
  public Order getById(long id) {
    return entityManager.find(Order.class, id);
  }

  /**
   * Update the passed user in the database.
   */
  public void update(Order user) {
    entityManager.merge(user);
    return;
  }

  /*
  * Set farmer for the order
  */
  public void updateFarmer(Farmer farmer, long id) {
    Order order = entityManager.find(Order.class, id);
    order.setFarmer(farmer);
    entityManager.merge(order);
    return;
  }

  public List<Order>  getHistoryByUserID(String id) {
    long id2 = Long.parseLong(id);
   return (List<Order>) entityManager.createQuery("from Order t where t.user.id = :userid")
   .setParameter("userid",id2)
   .getResultList();
  }
  // 


  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
}