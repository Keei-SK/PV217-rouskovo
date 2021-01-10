package cz.muni.fi.pv217.rouskovo;

import io.smallrye.jwt.build.Jwt;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashSet;

@Path("/token")
public class TokenGenerator {

    @GET
    @RolesAllowed("ADMIN")
    public String generateCustomerToken(String upn) {
        return generateToken(upn, new HashSet<>(Arrays.asList("CUSTOMER"));
    }

    @GET
    @RolesAllowed("ADMIN")
    public String generateAdminToken(String upn) {
        return generateToken(upn, new HashSet<>(Arrays.asList("ADMIN", "CUSTOMER")));
    }

    public String generateToken(String upn, HashSet<> groups) {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(upn)
                        .groups(groups)
                        .sign();
        return token;
    }

}