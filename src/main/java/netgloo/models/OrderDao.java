package netgloo.models;

import java.util.List;
import netgloo.models.Order;
import netgloo.models.Farmer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


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
        "from Order where orderNumber like :name")
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

  public Farmer findFarmer(Order order) {
    Farmer farmer = null;
    double dist = 10000;
    double orig_lat = order.getLatitude();
    double orig_lon = order.getLongitude();
    try{
      farmer = (Farmer)entityManager.createQuery("from Farmer u where (3956*2*ASIN(SQRT(POWER(SIN((:lat-abs(u.latitude))*pi()/180/2),2)+COS(:lat*pi()/180) * COS(abs(u.latitude)* pi()/180) *POWER(SIN((:long -u.longitude)* pi()/180/2),2))))  <= :dist")
      .setParameter("lat", orig_lat)
      .setParameter("long", orig_lon)
      .setParameter("dist", dist)
      .getSingleResult();
    } catch(Exception ex) {
      System.out.println("ada error di SQL");
      System.out.println(orig_lat);
      System.out.println(orig_lon);
      System.out.println(ex.toString());
    }

    return farmer;
  }

  // 

/* FINDING DISTANCE FARMER haversine
set @orig_lat = 37.334542;
set @orig_lon = -121.890821;
set @dist = 10;



UserGpsLocation users = (UserGpsLocation)em.createNativeQuery("select (3956*2*ASIN(SQRT(POWER(SIN((?1-abs(u.mlatitude))*pi()/180/2),2)+COS(?1*pi()/180) * COS(abs(u.mlatitude)* pi()/180) *POWER(SIN((?2 -u.mlogitude)* pi()/180/2),2)))) as distance from UserGpsLocation u having distance < :dist ORDER BY distance")
      .setParameter(1, mlatitude)
      .setParameter(2, mlogitude)
      .setParameter("dist", 10)
      .getResultList();
*/

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
}