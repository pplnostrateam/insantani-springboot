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
@Table(name = "vegetable")
public class Vegetable {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @NotNull
  private String name;
  
  @NotNull
  private int price;

  @NotNull
  private int stock;
  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  public Vegetable() { }

  public Vegetable(int id) { 
    this.id = id;
  }

  public Vegetable(String name, int price, int stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

   public long getId() {
    return id;
  }

  public void setId(int value) {
    this.id = value;
  }

  public String getName() {
    return name;
  }
  
  public void setName(String value) {
    this.name = value;
  }
  
  public int getPrice() {
    return price;
  }

  public void setPrice(int value) {
    this.price = value;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int value) {
    this.stock = value;
  }

} // class Vegetable
