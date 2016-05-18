package netgloo.models;

import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents an User for this web application.
 */

@Entity
public class User {

  @Id
  @NotNull
  @Size(max = 64)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @NotNull
  @Size(max = 64)
  @Column(name = "password", nullable = false)
  private String password;

  User() {
  }

  public User(final String id, final String password) {
    this.id = id;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            .add("id", id)
            .add("password", password)
            .toString();
  }
}