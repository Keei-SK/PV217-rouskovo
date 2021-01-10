package cz.muni.fi.pv217.rouskovo;

import io.smallrye.jwt.build.Jwt;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashSet;

@Path("/token")
public class TokenGenerator {

    @POST
    @RolesAllowed("ADMIN")
    @Path("/customer")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateCustomerToken(String upn) {
        return generateToken(upn, new HashSet<>(Arrays.asList("CUSTOMER")));
    }

    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateAdminToken(String upn) {
        return generateToken(upn, new HashSet<>(Arrays.asList("ADMIN", "CUSTOMER")));
    }

    public String generateToken(String upn, HashSet<String> groups) {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(upn)
                        .groups(groups)
                        .sign();
        return token;
    }

}