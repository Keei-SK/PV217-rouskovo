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
    public String name;
    public String address;
    public String phone;
    public String email;

    public Customer() {}

    public Customer(String username, String name, String address, String phone, String email) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public static Customer findByUsername(String username) {
        return find("username", username).firstResult();
    }

    @Override
    public String toString() {
        return "username: " + username + "\n" +
                "name: " + name + "\n" +
                "address: " + address + "\n" +
                "phone: " + phone + "\n" +
                "email: " + email + "\n";
    }
}
