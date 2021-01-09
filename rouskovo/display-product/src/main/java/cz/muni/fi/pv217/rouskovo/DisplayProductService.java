package cz.muni.fi.pv217.rouskovo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/display-product")
public class DisplayProductService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String echoDisplayProduct() {
        return "Hello RESTEasy \n Product displayed.\n";
    }
}