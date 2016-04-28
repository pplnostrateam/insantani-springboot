package netgloo.controllers;

import netgloo.models.*;
import netgloo.controllers.*;

import java.util.Map;

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
  public String createOrder(@RequestBody Map<String, String> payload) {
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
   		orderDao.create(order);//insert to database
   	}
   	catch (Exception ex) {
   		return "[failed \":" + ex.toString() + "]";
    }

    return "[\"voucher\":\""+ token+"\"]";
  }

  @Autowired
  private OrderDao orderDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private VegetableDao vegetableDao;

}
