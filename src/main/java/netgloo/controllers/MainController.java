package netgloo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  @RequestMapping("/")
  @ResponseBody
  public String index() {
    return "Spring boot mysql JPA Hibernate, read http://docs.insantaninostra.apiary.io for API docummentation";
  }


  @RequestMapping("/error")
  @ResponseBody
  public String error() {
    return "NOT FOUND";
  }

}
