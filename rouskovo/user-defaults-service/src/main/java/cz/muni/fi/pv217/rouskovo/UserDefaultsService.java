package cz.muni.fi.pv217.rouskovo;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.JsonbConfig;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/userdefaults")
public class UserDefaultsService {

    @Inject
    JsonWebToken jwt;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        String instructions = "Welcome to our e-shop \n" +
                "Register at '/register' \n" +
                "Already a customer? Head to '/login'";
        return instructions;
    }

    @GET
    @PermitAll
    @Path("/login")
    public String login(@Context SecurityContext ctx) {
        if (ctx.getUserPrincipal() == null) {
            return "Login using username and password";
        }
        if (ctx.isUserInRole("customer") || ctx.isUserInRole("admin")) {
            return "Generated Token";
        }
        return "Incorrect username or password";
    }

    @GET
    @PermitAll
    @Path("/register")
    public String registerInfo() {
        return "Choose a username and a password";
    }

    @POST
    @PermitAll
    @Path("/register")
    @Transactional
    public String registerCustomer(UserEntity entity) {

        // perform some integrity checks
        entity.role = Role.CUSTOMER;
        entity.persist();
        return "Welcome " + entity.username +
                "! Thank your for using our services";
    }

    @POST
    @RolesAllowed("admin")
    @Path("/admin")
    @Transactional
    public String createAdmin(UserEntity entity) {
        entity.role = Role.ADMIN;
        entity.persist();
        return "New admin " + entity.username +
                " added successfully";
    }

}