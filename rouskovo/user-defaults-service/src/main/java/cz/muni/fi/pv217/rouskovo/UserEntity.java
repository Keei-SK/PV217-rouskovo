package cz.muni.fi.pv217.rouskovo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class UserEntity extends PanacheEntity {

    @PrimaryKeyJoinColumn
    public String username;
    public String password;
    @JsonbProperty(nillable = true)
    @Enumerated(EnumType.STRING)
    public Role role;

    public UserEntity() {}

    public UserEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
