package netgloo.controllers;

import java.util.List;
import java.util.ArrayList;

import netgloo.models.Vegetable;
import netgloo.models.VegetableDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class VegetableController
 */
@Controller
public class VegetableController {
    @RequestMapping(value="/api/vegetable/create")
    @ResponseBody
    public String create(String name, int price, int stock) {
        try {
      		Vegetable user = new Vegetable(name, price, stock);
      		vegetableDao.create(user);
   		}
		catch (Exception ex) {
      		return "Error creating the vegetable: " + ex.toString();
        }
        return "Vegetable " + name + " succesfully created!";
	}

	@RequestMapping(value="/api/vegetable/find")	
	@ResponseBody
	public Vegetable find(String name) {
		Vegetable user;
		try {
			user = vegetableDao.getByName(name);
		}catch (Exception ex) {
      	    //return "User not found: " + ex.toString();
        	user = new Vegetable("vegetable not found" + ex.toString(),0,0);
        }
        return user;
	}

	@RequestMapping(value="/api/vegetable/sugesstion")
	@ResponseBody
	public List<Vegetable> sugesstion(String name) {
		List<Vegetable> result;
		try{
			result = vegetableDao.getSugesstionByName(name);
		} catch (Exception ex) {
			Vegetable x = new Vegetable("vegetable not found" + ex.toString(),0,0);
			List<Vegetable> asd = new ArrayList<Vegetable>();
			asd.add(x);
			return asd;
		}
		return result;
	}
	// ------------------------
    // PRIVATE FIELDS
    // ------------------------
  
    // Wire the UserDao used inside this controller.
    @Autowired
    private VegetableDao vegetableDao;

} // class Vegetable Controller