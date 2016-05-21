package netgloo.service;

import com.google.common.base.Optional;
import netgloo.models.User;
import netgloo.models.UserCreateForm;

import java.util.Collection;
import java.util.List;

/**
 * Created by arisyaag on 5/19/16.
 */
public interface UserService {
    Optional<User> getUserById(long id);
    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();
    User create(UserCreateForm form);

    User save(User user);
    List<User> getList();
}
