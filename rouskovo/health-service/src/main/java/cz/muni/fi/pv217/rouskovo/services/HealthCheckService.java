package cz.muni.fi.pv217.rouskovo.services;

import cz.muni.fi.pv217.rouskovo.objects.MicroServiceHealth;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.*;


@ApplicationScoped
public class HealthCheckService {
    private List<MicroServiceHealth> services = new ArrayList<>();

    public void loadServices(@Observes StartupEvent event) {
        MicroServiceHealth product_service = new MicroServiceHealth();
        product_service.name = "product-service";
        product_service.url = "http://localhost:8082";
        services.add(product_service);
    }

    private MicroServiceHealth checkService(MicroServiceHealth service) {
        try {
            Client client = ClientBuilder.newClient();
            Response response  = client.target(service.url)
                    .path("/health")
                    .request()
                    .get();

            MicroServiceHealth updated = response.readEntity(MicroServiceHealth.class);
            service.status = updated.status;
            service.checks = updated.checks;
            client.close();

        } catch (Exception ex) {
            service.status = "DOWN";
        }

        return service;
    }

    public List<MicroServiceHealth> healthCheckServices() {
        List<MicroServiceHealth> responses = new ArrayList<>();
        for (MicroServiceHealth service : services) {
            responses.add(checkService(service));
        }
        return responses;
    }

}
