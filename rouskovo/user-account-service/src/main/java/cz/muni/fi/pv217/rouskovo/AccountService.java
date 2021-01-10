package cz.muni.fi.pv217.rouskovo;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        return "";
    }

    @POST
    //@RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/info")
    @Transactional
    public String setDetails(Customer customer) {

        customer.username = "user"; // will be obtained from token
        customer.persist();
        return "Account information updated successfully";
    }

    @GET
    //@RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/info")
    public String showDetails() {

        return "Show account details";
    }

    @GET
    //@RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/myorders")
    public String showOrders() {

        return "Show account orders";
    }

}