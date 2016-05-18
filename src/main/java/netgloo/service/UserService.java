package netgloo.service;

import netgloo.models.User;

import java.util.List;

/**
 * Created by arisyaag on 5/19/16.
 */
public interface UserService {
    User save(User user);
    List<User> getList();
}
