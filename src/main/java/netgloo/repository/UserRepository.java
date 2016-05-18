package netgloo.repository;

import netgloo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arisyaag on 5/19/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
