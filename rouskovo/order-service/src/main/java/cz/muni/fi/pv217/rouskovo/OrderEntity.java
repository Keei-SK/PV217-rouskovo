package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class OrderEntity extends PanacheEntity{
    @NotBlank
    public String username;
    @NotNull
    public int productID;
    @Min(1)
    public int quantity;
    @Min(0)
    public Double totalPrice;
}