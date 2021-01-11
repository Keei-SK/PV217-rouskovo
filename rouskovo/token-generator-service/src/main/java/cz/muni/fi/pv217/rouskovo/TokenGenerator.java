package cz.muni.fi.pv217.rouskovo;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.HashSet;

@Path("/token")
public class TokenGenerator {

    @Inject
    JsonWebToken token;

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

    @POST
    @Path("/test")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String generateAdminToken(@Context SecurityContext ctx, String upn) {
        var principal = ctx.getUserPrincipal();
        return "Hello" + principal.getName();
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