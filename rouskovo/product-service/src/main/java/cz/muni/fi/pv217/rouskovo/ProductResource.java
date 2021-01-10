package cz.muni.fi.pv217.rouskovo;

import cz.muni.fi.pv217.rouskovo.entity.Product;
import cz.muni.fi.pv217.rouskovo.service.ProductService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.util.List;

@Path("/product")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<Product> getProducts() {
        return Product.listAll(Sort.ascending("id"));
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam long id) {
        Product product = Product.findById(id);

        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Product for id %d not found.", id))
                    .build();
        }

        return Response.ok(product).build();
    }

    @POST
    @Path("/create")
    public Product createProduct(Product product) {
        return productService.createProduct(product);
    }

    @PUT
    @Path("/{id}/update")
    public Product updateProduct(@PathParam long id, JsonObject update) {
        try {
            return productService.updateProduct(id, update);
        } catch (IllegalArgumentException ex) {
            throw new ClientErrorException(ex.getMessage(), Response
                    .status(Response.Status.PRECONDITION_FAILED)
                    .entity(ex.getMessage())
                    .build());
        }
    }

    @DELETE
    @Path("/{id}/delete")
    public Response deleteProduct(@PathParam long id) {
        Product product;
        try {
            product = productService.deleteProduct(id);

            if (product == null) {
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Cannot delete product with id " + id)
                        .build();
            }
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Product with id %d not found.", id))
                    .build();
        }

        return Response.ok(product).build();
    }


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello test";
    }
}