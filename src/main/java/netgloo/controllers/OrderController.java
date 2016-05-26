package netgloo.controllers;

import netgloo.models.*;
import netgloo.controllers.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Controller
public class OrderController {

  @RequestMapping(value = "api/order", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Order createOrder(@RequestBody Order payload) {
      Order order = new Order(payload.getUser(), payload.getVegetable(), payload.getLocation(),
              payload.getLatitude(), payload.getLongitude(), payload.getNote(), payload.getPrice(),
              payload.getToken(), payload.getQuantity());

      try {
          orderDao.create(order);
      } catch (Exception ex) { }

      return order;
  }

/*
 * This method use to find detail about order
 */
  @RequestMapping(value = "api/order/detail", method = RequestMethod.GET)
  @ResponseBody
  public Order detail(String ordernumber) {
    System.out.println(ordernumber);
    Order order;
    try {
      order = orderDao.getByToken(ordernumber);
      if(order.getFarmer() == null) {
        System.out.println("Order dont have farmers failed");
        Farmer farmer = orderDao.findFarmer(order);
        order.setFarmer(farmer);
        orderDao.update(order);
      }
      return order;
    }
    catch(Exception e) {
      order = new Order("Order not found");
    }
    return order;
  }

  @RequestMapping(value = "api/order/status", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONObject status(@RequestBody Map<String, String> payload) {
    String ordernumber = payload.get("ordernumber");
    JSONObject obj = new JSONObject();
    try {
      Order order = orderDao.getByToken(ordernumber);
      if(order == null) {
        System.out.println("bug");
      }
      order.setOrderStatus(4);
      orderDao.update(order);
      Vegetable vegetable = order.getVegetable();
      System.out.println("vegetable stock " + vegetable.getStock());
      System.out.println("order quantity " + order.getQuantity());
      vegetable.setStock(vegetable.getStock() - order.getQuantity());
      vegetableDao.update(vegetable);
      Vegetable vegetable2 = vegetableDao.getById(vegetable.getId());
      System.out.println("new vegetable stock " + vegetable.getStock());

      obj.put("orderstatus", ""+order.getOrderStatus());
    }
    catch(Exception e) {
      obj.put("orderstatus", "order " + ordernumber + " not found");
    }
    return obj;
  }

/*
 * Finding the list of order history of user
 */
  @RequestMapping(value = "api/order", method = RequestMethod.GET)
  @ResponseBody
  public List<Order> findhistory(String userid) {
    //String userid = payload.get("userid");
    System.out.println("find order list :" + userid);
    List<Order> result;
    try{
      result = orderDao.getHistoryByUserID(userid);
    } catch (Exception ex) {
      System.out.println("error " +  ex.toString());
      result = new ArrayList<Order>();
    }
    return result;
  }
  /*
  * Finding available farmer for this order
  */
  public void findFarmer(Order order) {
    long id = 1;
    order.setFarmer(farmerDao.getById(id));
  }


  @Autowired
  private OrderDao orderDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private VegetableDao vegetableDao;
  @Autowired
  private FarmerDao farmerDao;

}
