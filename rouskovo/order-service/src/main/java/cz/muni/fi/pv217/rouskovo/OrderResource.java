package cz.muni.fi.pv217.rouskovo;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
public class OrderResource {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public OrderEntity createOrder() {
        OrderEntity newOrder = new OrderEntity();

        newOrder.username = "test0";
        newOrder.productID = "1";
        newOrder.quantity = 10;
        newOrder.totalPrice = 120;

        newOrder.persist();
        return newOrder;
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