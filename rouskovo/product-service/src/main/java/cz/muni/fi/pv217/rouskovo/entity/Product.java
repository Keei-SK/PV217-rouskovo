package cz.muni.fi.pv217.rouskovo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Product extends PanacheEntity {
    public ProductType type;
    public String name;
    public String description;
    public Double price;

}
