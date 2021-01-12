package cz.muni.fi.pv217.rouskovo.service;

import cz.muni.fi.pv217.rouskovo.entity.Product;
import cz.muni.fi.pv217.rouskovo.entity.ProductType;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class ProductService {

    @Transactional
    public Product createProduct(Product product) {
        product.persist();
        return product;
    }

    @Transactional
    public Product updateProduct(long id, JsonObject updateJson) {
        Product product = Product.findById(id);

        if (product == null) {
            return null;
        }

        updateJson.entrySet().forEach(entry -> {
            String value = entry.getValue().toString().replace("\"", "");
            switch (entry.getKey()) {
                case "id":
                    product.id = Long.valueOf(value);
                    break;
                case "type":
                    product.type = ProductType.valueOf(value);
                    break;
                case "name":
                    product.name = value;
                    break;
                case "description":
                    product.description = value;
                    break;
                case "price":
                    product.price = Double.valueOf(value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown property provided for update product: " + entry.getKey());
            }
        });

        product.persist();
        return product;
    }

    @Transactional
    public Product deleteProduct(long id) {
        Product product = Product.findById(id);

        if (product == null) {
            throw new NotFoundException("Cannot find product with id " + id);
        }

        boolean deleted = Product.deleteById(id);
        return deleted ? product : null;
    }
}
