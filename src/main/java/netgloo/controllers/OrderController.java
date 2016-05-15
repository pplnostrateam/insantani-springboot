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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Controller
public class OrderController {

  @RequestMapping(value = "api/order/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JSONObject createOrder(@RequestBody Map<String, String> payload) {

    JSONObject obj = new JSONObject();
    System.out.println("payload " + payload );

    long user = Long.parseLong(payload.get("user"));
    int vegetable = Integer.parseInt(payload.get("vegetable"));
    String location = payload.get("location");
    double latitude = Double.parseDouble(payload.get("latitude"));
    double longitude = Double.parseDouble(payload.get("longitude"));
    String note = payload.get("note");
    Order order;
    String token;


   	try{
   		User pembeli = userDao.getById(user); 
   		Vegetable sayur = vegetableDao.getById(vegetable);
   		int harga = sayur.getPrice();
      int quantity = sayur.getStock();
      token = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0,5);
      System.out.println(pembeli.getEmail());
      System.out.println(sayur.getName());
   		order = new Order(pembeli, sayur,
     	location, latitude, longitude, note, harga, token, quantity);
    //  findFarmer(order); //set farmer for order
   		orderDao.create(order);//insert to database
      obj.put("ordernumber",token);
   	}
   	catch (Exception ex) {
   		obj.put("ordernumber","Sorry failed to create order");
    }
    return obj;
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
  public JSONObject status(String ordernumber) {
    JSONObject obj = new JSONObject();
    try {
      Order order = orderDao.getByToken(ordernumber);
      order.setOrderStatus(4);
      orderDao.update(order);
      Vegetable vegetable = order.getVegetable();
      vegetable.setStock(vegetable.getStock() - order.getQuantity());
      vegetableDao.update(vegetable);
      obj.put("orderstatus", ""+order.getOrderStatus());
    }
    catch(Exception e) {
      obj.put("orderstatus", "order not found");
    }
    return obj;
  }

/*
 * Finding the list of order history of user
 */
  @RequestMapping(value="api/order/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Order> findhistory(String userid) {
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
