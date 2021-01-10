package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class Order extends PanacheEntity {
    public String username;
    public Integer productID;
    public Integer quantity;
    public Integer totalPrice;
}
