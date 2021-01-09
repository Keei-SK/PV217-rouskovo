package cz.muni.fi.pv217.rouskovo;

import javax.annotation.security.PermitAll;
import javax.json.bind.JsonbConfig;
import javax.transaction.Transactional;
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
        String instructions = "Welcome to our e-shop \n" +
                "Register at '/register' \n" +
                "Already a customer? Head to '/login'";
        return instructions;
    }

    @GET
    @PermitAll
    @Path("/register")
    public String info() {
        return "Choose a username and a password";
    }

    @POST
    @PermitAll
    @Path("/register")
    @Transactional
    public String registerCustomer(UserEntity newEntity) {

        // perform some integrity checks
        newEntity.role = "Customer";
        newEntity.persist();
        return "Welcome " + newEntity.username +
                "! Thank your for using our services";
    }

}