package cz.muni.fi.pv217.rouskovo.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@Liveness
@ApplicationScoped
public class ProductServiceHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        if (new Random().nextBoolean()) {
            return HealthCheckResponse.up("Server is up.");
        } else {
            return HealthCheckResponse.down("Server is down.");
        }
    }

}
