package netgloo.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "orders")
public class Order {

    final static int CREATED = 1;
    final static int PREPARING = 2;
    final static int DELIVERED = 3;
    final static int COMPLETED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String orderNumber;

  	@NotNull
    private Date created;
  	
  	@ManyToOne
  	private User user;

    @ManyToOne
    private Vegetable vegetable;

    @NotNull
    private String location;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private int orderStatus;
    @NotNull
    private String note;
    @NotNull
    private int price;
    @NotNull
    private int quantity;

    @ManyToOne
    private Farmer farmer;

    public Order() { //jpa only

    }

    public Order(String note) {
        this.note = note;
    }

    public Order(User user, Vegetable vegetable,
     String location, double latitude, double longitude, String note, int price, String token, int quantity) {

        this.orderNumber = token;
        this.orderStatus = CREATED;
        this.user = user;
        this.vegetable = vegetable;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.price = price;
        this.created = new Date();
    }


	@PrePersist
 	protected void onCreate() {
   	    this.created = new Date();
    }
	
    public User getUser() {
        return user;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public User getId() {
        return user;
    }

    public Date getCreated() {
        return created;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getNote() {
        return note;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }
}