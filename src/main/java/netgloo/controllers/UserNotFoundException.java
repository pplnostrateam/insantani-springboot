package netgloo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by arisyaag on 5/23/16.
 */

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No User Found")
public class UserNotFoundException extends RuntimeException {

}
