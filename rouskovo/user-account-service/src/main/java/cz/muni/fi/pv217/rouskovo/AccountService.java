package cz.muni.fi.pv217.rouskovo;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/account")
@RequestScoped
public class AccountService {

    @Inject
    private JsonWebToken jwt;

    @POST
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/info")
    @Transactional
    public String setDetails(@Context SecurityContext ctx, Customer customer) {

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