package netgloo.exception;

/**
 * Created by arisyaag on 5/19/16.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}