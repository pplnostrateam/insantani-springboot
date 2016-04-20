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
import javax.persistence.OneToOne;

@Entity
@Table(name = "pemesanan")
public class Order {

    final static int CREATED = 1;
    final static int PREPARING = 2;
    final static int DELIVERED = 3;
    final static int COMPLETED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String token;

  	@NotNull
    private Date created;
  	
  	@OneToOne
  	private User user;

    @OneToOne
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

    public Order() { //jpa only

    }

    public Order(User user, Vegetable vegetable,
     String location, double latitude, double longitude, String note, int price, String token) {

        this.token = token;
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

    public String getToken() {
        return token;
    }

    public User getId() {
        return user;
    }

    public Vegetable getCreated() {
        return vegetable;
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
}