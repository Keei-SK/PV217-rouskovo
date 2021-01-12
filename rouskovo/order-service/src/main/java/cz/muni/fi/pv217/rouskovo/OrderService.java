package cz.muni.fi.pv217.rouskovo;

import cz.muni.fi.pv217.rouskovo.entity.OrderEntity;
import cz.muni.fi.pv217.rouskovo.entity.ProductEntity;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/order")
@ApplicationScoped
public class OrderService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed(name = "defaultServiceRequestTimer", description = "A measure of how long it takes to get a response from a server for standard request.", unit = MetricUnits.MILLISECONDS)
    public String orderInfo() {
        return "To create an order use HTTP method POST on 'current_address/new' like: \n\t'http POST 0.0.0.0:8083/order/new username=pabloescobar productID=123456789 quantity=20'\n\n" +
                "To cancel order use HTTP method POST on 'current_address/order_id/cancel' like: \n\t 'http POST :8083/order/1/cancel'\n\n" +
                "To show metrics use HTTP method GET on 'current_address/metrics' like: \n\t 'http :8083/metrics' \n\n" +
                "For less detailed metrics use: \n\t http :8083/metrics/application Accept:application/json\n\n";
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Timed(name = "createOrderTimer", description = "A measure of how long it takes to create an order.", unit = MetricUnits.MILLISECONDS)
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
        System.out.println("Created order with id: " + newOrder.id);
        return Response.ok(newOrder).build();
    }

    @Timed(name = "getProductDetailsTimer", description = "A measure of how long it takes to get a product ID.", unit = MetricUnits.MILLISECONDS)
    private ProductEntity getProductID(Integer id){

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://127.0.0.1:8082")
                .path("/product/"+id.toString())
                .request()
                .get();
        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(ProductEntity.class);
    }


    @POST
    @Path("/{id}/cancel")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Counted
    public Response cancelOrder(@PathParam("id") int id) {
        OrderEntity orderRecord;
        try {
            orderRecord = deleteOrder(id);

            if (orderRecord == null) {
                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Cannot delete order with id " + id)
                        .build();
            }
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Order with id %d not found.", id))
                    .build();
        }

        System.out.println("Canceled order with id: " + orderRecord.id);
        return Response.ok(orderRecord).build();
    }

    private OrderEntity deleteOrder(long id) {
        OrderEntity foundOrder = OrderEntity.findById(id);

        if (foundOrder == null) {
            throw new NotFoundException("Cannot find order with id " + id);
        }

        boolean deleted = OrderEntity.deleteById(id);
        return deleted ? foundOrder : null;
    }
}