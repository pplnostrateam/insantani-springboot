package netgloo.repository;

import com.google.common.base.Optional;
import netgloo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arisyaag on 5/19/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
}
