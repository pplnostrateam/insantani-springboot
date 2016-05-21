package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import netgloo.models.Vegetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Class UserController
 */
@RestController
public class UserController {

  @RequestMapping(value="api/user/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  User create(@RequestBody User input) throws Exception {
    User user = new User(input.getEmail(), input.getName(), input.getPassword(), input.getPhone());
    try {
      userDao.create(user);
    }
    catch (Exception ex) {

    }
    return user;
  }

  @RequestMapping(value="api/user/find/{email}", method = RequestMethod.GET)
  @ResponseBody
  public User getByEmail(String email) throws Exception {
    User user = null;
    try {
      user = userDao.getByEmail(email);
    }
    catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
    return user;
  }

    @RequestMapping(value="api/user/login", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody User input) throws Exception {
        User user = null;

        System.out.println(input.getEmail());
        System.out.println(input.getName());

        try {
            user = userDao.getByEmailAndPassword(input.getEmail(), input.getPassword());

        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }

    return user;
  }
  
  // Wire the UserDao used inside this controller.
  @Autowired
  private UserDao userDao;
  
} // class UserController
