package cz.muni.fi.pv217.rouskovo;

import cz.muni.fi.pv217.rouskovo.objects.MicroServiceHealth;
import cz.muni.fi.pv217.rouskovo.services.HealthCheckService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/healthcheck")
@ApplicationScoped
public class HealthResource {
    
    @Inject
    HealthCheckService healthCheckService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MicroServiceHealth> makeHealthCheck() {
        return healthCheckService.healthCheckServices();
    }
}