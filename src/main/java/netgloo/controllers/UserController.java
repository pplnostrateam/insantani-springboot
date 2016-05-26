package netgloo.controllers;

import netgloo.models.User;
import netgloo.models.UserDao;

import netgloo.models.Vegetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class UserController
 */
@Controller
@RequestMapping(value = "api/user")
public class UserController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="", method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public User create(@RequestBody User input) throws Exception {
      User user = new User(input.getEmail(), input.getName(), input.getPassword(), input.getPhone());

      try {
          userDao.create(user);
      } catch (Exception ex) { }

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
  @RequestMapping(value="/find", method = RequestMethod.GET)
  @ResponseBody
  public User getByEmail(String email) throws Exception {
    // String userId;
    User user = null;
    try {
      user = userDao.getByEmail(email);
      // userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      throw new UserNotFoundException();
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

  @RequestMapping(value="/login", method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public User login(@RequestBody User input) throws Exception {
      User user = null;

      try {
          user = userDao.getByEmailAndPassword(input.getEmail(), input.getPassword());
      } catch(Exception ex) {
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
