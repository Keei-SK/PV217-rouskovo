package cz.muni.fi.pv217.rouskovo;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/userdefaults")
public class UserDefaultsService {

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
        var principal = ctx.getUserPrincipal();
        if (principal == null) {
            return "Login using username and password";
        }
        Client client = ClientBuilder.newClient();
        String path = "/token";
        String json = getJson("upn", principal.getName());
        path += (ctx.isUserInRole(Role.ADMIN.name())) ? "/admin" : "/customer";
        Response response = client.target("http://localhost:8087")
                .path("/token/")
                .request()
                .header("Authorization", "Bearer " + getOwnToken())
                .post(Entity.json(json));
        if (response.getStatus() == 200) {
            return "Token request failed with status code " + response.getStatus();
        }
        return response.readEntity(String.class);
    }

    public String getJson(String key, String value) {
        return Json.createObjectBuilder()
                .add(key, value)
                .build()
                .toString();
    }

    public String getOwnToken() {
        return "test";
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
    @RolesAllowed("ADMIN")
    @Path("/admin")
    @Transactional
    public String createAdmin(UserEntity entity) {
        entity.role = Role.ADMIN;
        entity.persist();
        return "New admin " + entity.username +
                " added successfully";
    }

}