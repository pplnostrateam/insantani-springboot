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

@Controller
public class OrderController {

  @RequestMapping(value = "api/order/start", method = RequestMethod.POST)
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
      token = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0,5);
      System.out.println(pembeli.getEmail());
      System.out.println(sayur.getName());
   		order = new Order(pembeli, sayur,
     	location, latitude, longitude, note, harga, token);
      findFarmer(order); //set farmer for order
   		orderDao.create(order);//insert to database
      obj.put("voucher",token);
   	}
   	catch (Exception ex) {
   		obj.put("failed",ex.toString());
    }
    return obj;
  }

/*
 * This method use to find detail about order
 */
  @RequestMapping(value = "api/order/detail", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject detail(String voucher) {
    JSONObject obj = new JSONObject();
    try {
      Order order = orderDao.getByToken(voucher);
      obj.put("farmerContact", order.getFarmer().getPhoneNumber());
      obj.put("kodePemesanan", voucher);
      obj.put("farmerName", order.getFarmer().getName());
      obj.put("status", ""+order.getOrderStatus());
    }
    catch(Exception e) {
      obj.put("farmerContact", "Null");
      obj.put("kodePemesanan", "Null");
      obj.put("farmerName", "Null");
      obj.put("status", "order not found");
    }
    return obj;
  }

  @RequestMapping(value = "api/order/status", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject status(String voucher) {
    JSONObject obj = new JSONObject();
    try {
      Order order = orderDao.getByToken(voucher);
      obj.put("status", ""+order.getOrderStatus());
    }
    catch(Exception e) {
      obj.put("status", "order not found");
    }
    return obj;
  }

/*
 * Finding the list of order history of user
 */
  @RequestMapping(value="api/order/findhistory", method = RequestMethod.GET)
  @ResponseBody
  public List<JSONObject> findhistory(String userid) {
    List<Order> result;
    List<JSONObject> parentData = new ArrayList<JSONObject>();
    try{
      result = orderDao.getHistoryByUserID(userid);
    } catch (Exception ex) {
      System.out.println("error " +  ex.toString());
      result = new ArrayList<Order>();
    }
    
    for(Order x : result) {
      JSONObject current = new JSONObject();
      current.put("location", x.getLocation());
      current.put("vegetable", x.getVegetable().getName());
      current.put("user", x.getUser().getName());
      current.put("note", x.getNote());
      current.put("price", x.getPrice());
      parentData.add(current);
    }

    return parentData;
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
