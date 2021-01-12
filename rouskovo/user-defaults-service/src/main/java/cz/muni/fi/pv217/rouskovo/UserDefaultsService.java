package cz.muni.fi.pv217.rouskovo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Collectors;

@Path("/userdefaults")
@Tags(value = @Tag(name = "Welcome to our e-shop \n" +
        "Register at '/register' \n" +
        "Already a customer? Head to '/login'"))
@SecurityScheme(securitySchemeName = "Basic", type = SecuritySchemeType.HTTP, scheme = "Basic")
public class UserDefaultsService {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Show welcome message")
    public String welcome() {
        String instructions = "Welcome to our e-shop \n" +
                "Register at '/register' \n" +
                "Already a customer? Head to '/login'";
        return instructions;
    }

    @GET
    @RolesAllowed({"ADMIn", "CUSTOMER"})
    @SecurityRequirement(name = "Basic", scopes = {})
    @Path("/login")
    @Operation(summary = "Login into your account")
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
                .path(path)
                .request()
                .header("Authorization", "Bearer " + getOwnToken())
                .post(Entity.json(json));
        if (response.getStatus() != 200) {
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

    public static String getOwnToken() {
        /*
        String token = new Scanner(UserDefaultsService.class.getClassLoader().getResourceAsStream("/admin.token"), "UTF-8").useDelimiter("\\A").next();
        return token;
         */
        // issues in native, quick fix
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuY29tL2lzc" +
                "3VlciIsInVwbiI6ImFkbWluIiwiZ3JvdXBzIjpbIkNVU1RPTUVSIiwiQURNSU4iXSwiaWF0IjoxNjEwM" +
                "zY3ODI2LCJleHAiOjE2MTA5NzI2MjYsImp0aSI6IklLdUczYk1qZzQ0Q25fRW44cDk4WlEifQ.Nzt8ReUil" +
                "EElo1doK1zhSuKEl6M7nE4reyHrD3uSis6uoYhc0DQGK19fmVeI8LXPrsnujYI51xRgRpC_g7opDhYCh0OWCNN" +
                "ubUALzDDaffbhuHifWPVgXLPJgudONJQVAh0Eg3TtxCeffcnHVLS2zQKoMKn3_7G_2ClcfwytMIV1LRtdK_dbc" +
                "NlsdWZ0fQMuVWjtlo2UWpabPQFVw-AjAQcEXh-qBndYlj5Sy90HGNNjnClxBg9bdjPRhdOpK6QHkeg-D4omC9OM5Ov" +
                "sniFkOM71ukSN5wRZ8Z7QulyJeuA4Af06q1JY5qN9sY8PMKR-lE6_2cxqNQ8NPf0B_EH5KQ";
    }

    @GET
    @PermitAll
    @Path("/register")
    @Operation(summary = "Show register information")
    public String registerInfo() {
        return "Choose a username and a password";
    }

    @POST
    @PermitAll
    @Path("/register")
    @Transactional
    @Operation(summary = "Register a new customer account")
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
    @Operation(summary = "Register an admin account")
    @SecurityRequirement(name = "Basic", scopes = {})
    public String createAdmin(UserEntity entity) {
        entity.role = Role.ADMIN;
        entity.persist();
        return "New admin " + entity.username +
                " added successfully";
    }

}