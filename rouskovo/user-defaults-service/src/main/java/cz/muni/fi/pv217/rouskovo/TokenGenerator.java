package cz.muni.fi.pv217.rouskovo;

// import io.smallrye.jwt.build.Jwt;

import java.util.Arrays;
import java.util.HashSet;

public final class TokenGenerator {

    public static String generateCustomerToken(String upn) {
        return "test";
    }

    public static String generateAdminToken(String upn) {
        return "test";
    }
    /*
    public static String generateCustomerToken(String upn) {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(upn)
                        .groups(Role.CUSTOMER.name())
                        .sign();
        return token;
    }

    public static String generateAdminToken(String upn) {
        String token =
                Jwt.issuer("https://example.com/issuer")
                        .upn(upn)
                        .groups(new HashSet<>(Arrays.asList(Role.CUSTOMER.name(), Role.ADMIN.name())))
                        .sign();
        return token;
    }
    */
}
