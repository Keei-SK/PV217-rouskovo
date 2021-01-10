package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class OrderEntity extends PanacheEntity{
    public String username;
    public String productID;
    public int quantity;
    public int totalPrice;
}
