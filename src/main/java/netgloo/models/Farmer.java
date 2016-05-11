package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Represents an User for this web application.
 */
@Entity
@Table(name = "farmers")
public class Farmer {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  private String email;
  
  @NotNull
  private String name;

  private String password;

  private String location;

  private String phoneNumber;

  @NotNull
  private double latitude;
  @NotNull
  private double longitude;


  //private Set<Order> orders;
  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  public Farmer() { }

  public Farmer(long id) { 
    this.id = id;
  }

  public Farmer(String email, String name, String password, String location, String phoneNumber, double latitude, double longitude) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.location = location;
    this.phoneNumber = phoneNumber;
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Farmer(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.longitude = 0;
    this.latitude = 0;
  }
  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String value) {
    this.email = value;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }
  
  public String getLocation() {
    return location;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

} // class User
