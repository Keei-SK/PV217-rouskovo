package cz.muni.fi.pv217.rouskovo.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class OrderHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call(){
        return HealthCheckResponse.up("OrderService Health Check");
    }
}
