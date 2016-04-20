package netgloo.controllers;

import netgloo.models.*;
import netgloo.controllers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

  @RequestMapping(value = "api/order/start")
  @ResponseBody
  public String createOrder(long user, int vegetable,
    String location, double latitude, double longitude, String note) {
   		
      System.out.println("NOTE " + note );
   	Order order;
   	try{
   		User pembeli = userDao.getById(user); 
   		Vegetable sayur = vegetableDao.getById(vegetable);
   		int harga = sayur.getPrice();
      String token = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0,5);
      System.out.println(pembeli.getEmail());
      System.out.println(sayur.getName());
   		order = new Order(pembeli, sayur,
     		location, latitude, longitude, note, harga, token);
   		orderDao.create(order);
   	}
   	catch (Exception ex) {
   		return "failed " + ex.toString();
    }

    return order.getToken();
  }

  @Autowired
  private OrderDao orderDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private VegetableDao vegetableDao;

}
