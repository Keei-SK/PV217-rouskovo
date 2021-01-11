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

        customer.username = getUsername(ctx);
        customer.persist();
        return "Account information updated successfully";
    }

    @GET
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/info")
    @Produces(MediaType.TEXT_PLAIN)
    public String showDetails(@Context SecurityContext ctx) {
        Customer c = Customer.findByUsername(getUsername(ctx));
        return "Account details\n" + c.toString();
    }

    @GET
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Path("/myorders")
    public String showOrders() {
        return "Show account orders";
    }

    public String getUsername(SecurityContext ctx) {
        return ctx.getUserPrincipal().getName();
    }

}