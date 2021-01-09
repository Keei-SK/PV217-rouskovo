package cz.muni.fi.pv217.rouskovo;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/userdefaults")
public class UserDefaultsResource {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String welcome() {
        String instructions = "Register at '/register' \nAlready a customer? Head to '/login'";
        return instructions;
    }

    @GET
    @PermitAll
    @Path("/register")
    public String info() {
        return "Choose a username and a password";
    }

    @POST
    @Path("/register")
    public String register(String username, String password) {
        return "test";
    }

}