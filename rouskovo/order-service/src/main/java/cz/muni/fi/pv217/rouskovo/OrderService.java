package cz.muni.fi.pv217.rouskovo;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
public class OrderService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String newOrder() {
        return "Hello RESTEasy new";
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