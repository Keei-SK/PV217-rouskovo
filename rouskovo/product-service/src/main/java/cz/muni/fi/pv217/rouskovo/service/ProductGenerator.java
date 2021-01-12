package cz.muni.fi.pv217.rouskovo.service;

import cz.muni.fi.pv217.rouskovo.entity.Product;
import cz.muni.fi.pv217.rouskovo.entity.ProductType;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProductGenerator {

    private static void insertProduct(ProductType type, String name, String description, Double price) {
        Product product = new Product();
        product.type = type;
        product.name = name;
        product.description = description;
        product.price = price;
        product.persist();
    }
    @Transactional
    public void loadProducts(@Observes StartupEvent event) {
        insertProduct(ProductType.MASK, "mask 1", "amazing mask", 10.0);
        insertProduct(ProductType.MASK, "mask 2", "awesome mask", 20.0);
        insertProduct(ProductType.MASK, "mask 3", "mask that looks like respirator", 66.6);
        insertProduct(ProductType.RESPIRATOR, "respirator 1", "Jesus wore this respirator", 33.0);
        insertProduct(ProductType.RESPIRATOR, "respirator 2", "made in china", 1.0);
    }
}
