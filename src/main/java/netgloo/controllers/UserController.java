package netgloo.controllers;

import netgloo.exception.UserAlreadyExistsException;
import netgloo.models.User;
import netgloo.models.UserDao;

import netgloo.models.Vegetable;
import netgloo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

/**
 * Class UserController
 */

//@Controller
@RestController
public class UserController {


  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  @Inject
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public User createUser(@RequestBody @Valid final User user) {
    LOGGER.debug("Received request to create the {}", user);
    return userService.save(user);
  }

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public List<User> listUsers() {
    LOGGER.debug("Received request to list all users");
    return userService.getList();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.CONFLICT)
  public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
    return e.getMessage();
  }

  /*
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
  */

  /*
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
  */
  

  /*
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
  */

 /*
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
  */

  /*
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
  
  // Wire the UserDao used inside this controller.
  @Autowired
  private UserDao userDao;
  */

  
} // class UserController
