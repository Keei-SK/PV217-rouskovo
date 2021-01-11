package cz.muni.fi.pv217.rouskovo;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/account")
@RequestScoped
@RolesAllowed({"CUSTOMER", "ADMIN"})
@Tags(value = @Tag(name = "Customer Account"))
@SecurityRequirement(name = "jwt", scopes = {"global"})
public class AccountService {

    @Inject
    private JsonWebToken jwt;

    @GET
    @Path("/info")
    @SecurityRequirement(name = "jwt", scopes = {})
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Current account information", content = @Content(schema = @Schema(implementation = Customer.class)))})
    @Operation(summary = "View account information")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewCustomerDetails(@Context SecurityContext ctx) {
        Customer c = Customer.findByUsername(getUsername(ctx));
        return "Account details\n" + c.toString();
    }

    @POST
    @Path("/info")
    @Transactional
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully set account information", content = @Content(schema = @Schema(implementation = Customer.class)))})
    @Operation(summary = "Set account information")
    @Produces(MediaType.TEXT_PLAIN)
    public String setCustomerDetails(@Context SecurityContext ctx, Customer customer) {

        customer.username = getUsername(ctx);
        customer.persist();
        return "Account information updated successfully";
    }

    @PUT
    @Path("/info")
    @Transactional
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully Updated account information", content = @Content(schema = @Schema(implementation = Customer.class)))})
    @Operation(summary = "Update account information")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCustomer(@Context SecurityContext ctx, Customer customer) {
        Customer c = Customer.findByUsername(getUsername(ctx));
        // update
        return "Account information has been successfully updated";
    }


    @DELETE
    @Path("/info")
    @Transactional
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Succesfully deleted account", content = @Content(schema = @Schema(implementation = Customer.class)))})
    @Operation(summary = "Deletes customer's account")
    public String deleteCustomer(@Context SecurityContext ctx, Customer custome) {
        Customer c = Customer.findByUsername(getUsername(ctx));
        c.delete();
        return "Your account info has been successfully deleted";
    }

    @GET
    @Path("/myorders")
    public String showOrders() {
        return "Show account orders";
    }

    public String getUsername(SecurityContext ctx) {
        return ctx.getUserPrincipal().getName();
    }

}