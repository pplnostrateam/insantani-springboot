package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import netgloo.models.Vegetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class UserController
 */
@Controller
public class UserController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="api/user/create")
  @ResponseBody
  public User create(String email, String name, String password) throws Exception {
    User user = new User(email, name, password);
    try {
      userDao.create(user);
    }
    catch (Exception ex) {

    }
    return user;
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="api/user/delete")
  @ResponseBody
  public User delete(long id) throws Exception {
    User user = new User(id);
    try {
      userDao.delete(user);
    }
    catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
    return user;
  }
  
  /**
   * Retrieve the id for the user with the passed email address.
   */
  @RequestMapping(value="api/user/find")
  @ResponseBody
  public User getByEmail(String email) throws Exception {
    // String userId;
    User user = null;
    try {
      user = userDao.getByEmail(email);
      // userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      // return "User not found: " + ex.toString();
      throw new Exception(ex.getMessage());
    }
    // return "The user id is: " + userId;
    return user;
  }

  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @RequestMapping(value="api/user/update")
  @ResponseBody
  public User updateName(long id, String email, String name) throws Exception {
    User user;

    user = userDao.getById(id);
    user.setEmail(email);
    user.setName(name);

    try {
      userDao.update(user);
    }
    catch (Exception ex) {
      throw new Exception(ex.getMessage());
      // return "Error updating the user: " + ex.toString();
    }

    return user;
  } 

  @RequestMapping(value="api/user/login")
  @ResponseBody
  public User login(String email, String password) throws Exception {
    User user = null;
    try {
      user = userDao.getByEmailAndPassword(email,password);
    }
    catch(Exception ex) {
      throw new Exception(ex.getMessage());
    }
    return user;
  }

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  // Wire the UserDao used inside this controller.
  @Autowired
  private UserDao userDao;
  
} // class UserController
