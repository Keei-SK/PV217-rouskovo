package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;

@Entity
public class UserEntity extends PanacheEntity {

    public String username;
    public String password;
    @JsonbProperty(nillable = true)
    public String role;

    public UserEntity() {}

    public UserEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
