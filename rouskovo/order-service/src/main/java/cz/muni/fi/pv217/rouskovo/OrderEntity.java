package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
public class OrderEntity extends PanacheEntity{
    public String username;

    public int productID;

    @Min(1)
    public int quantity;

    public Double totalPrice;
}