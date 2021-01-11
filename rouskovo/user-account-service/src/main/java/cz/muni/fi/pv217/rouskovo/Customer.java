package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Customer extends PanacheEntity {
    @PrimaryKeyJoinColumn
    @JsonbProperty(nillable = true)
    public String username;
    public String address;
    public String phone;
    public String email;

    public Customer() {}
}
