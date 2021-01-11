package cz.muni.fi.pv217.rouskovo;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/order")
public class OrderResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String orderInfo() {
        return "To create an order use HTTP method POST on 'current_address/new' like: \n\t'http POST 0.0.0.0:8083/order/new username=pabloescobar productID=123456789 quantity=20\n\n" +
                "To cancel order use HTTP method POST on 'current_address/cancel' like: \n\t 'TBD....'\n\n" +
                "To show metrics use HTTP method GET on 'current_address/metrics' like: \n\t 'http GET 0.0.0.0:8083/order/metrics'\n\n" +
                "\n" +
                "Thank you.";
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createOrder(@Valid OrderEntity newOrder) {

        ProductEntity product = getProductID(newOrder.productID);
        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Product for id %d not found.", newOrder.productID))
                    .build();
        }

        newOrder.totalPrice = product.price * newOrder.quantity;
        newOrder.persist();

        return Response.ok(newOrder).build();
    }

    private ProductEntity getProductID(Integer id){
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://127.0.0.1:8082")
                .path("/product/"+id.toString())
                .request()
                .get();

        System.out.println(response.getStatus());
        if (response.getStatus() != 200) {
            return null;
        }

        ProductEntity product = response.readEntity(ProductEntity.class);
        System.out.println(product);
        return product;
    }



    @GET
    @Path("/cancel")
    @Produces(MediaType.TEXT_PLAIN)
    public String cancelOrder() {
        return "Hello RESTEasy cancel";
    }





    @GET
    @Path("/metrics")
    @Produces(MediaType.TEXT_PLAIN)
    public String metrics() {
        return "Hello RESTEasy metrics";
    }
}