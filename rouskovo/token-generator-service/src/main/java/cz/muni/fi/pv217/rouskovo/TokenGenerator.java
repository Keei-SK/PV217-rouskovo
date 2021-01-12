package cz.muni.fi.pv217.rouskovo;

import io.smallrye.jwt.build.Jwt;

import javax.annotation.security.RolesAllowed;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Path("/token")
public class TokenGenerator {

    @POST
    @RolesAllowed("ADMIN")
    @Path("/customer")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String generateCustomerToken(JsonObject json) {
        return generateToken(json.getString("upn"), new HashSet<>(Arrays.asList("CUSTOMER")));
    }

    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String generateAdminToken(JsonObject json) {
        return generateToken(json.getString("upn"), new HashSet<>(Arrays.asList("ADMIN", "CUSTOMER")));
    }

    public String generateToken(String upn, HashSet<String> groups) {
        System.out.println(upn);
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(upn)
                        .groups(groups)
                        .sign();
        return token;
    }

}