package test.util;

import netgloo.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arisyaag on 5/19/16.
 */
public class UserUtil {

    private static final String ID = "id";
    private static final String PASSWORD = "password";

    private UserUtil() {
    }

    public static User createUser() {
        return new User(ID, PASSWORD);
    }

    public static List<User> createUserList(int howMany) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            userList.add(new User(ID + "#" + i, PASSWORD));
        }
        return userList;
    }

}
