package cz.muni.fi.pv217.rouskovo;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class OrderService {
    @Transactional
    public OrderEntity deleteOrder(long id) {
        OrderEntity foundOrder = OrderEntity.findById(id);

        if (foundOrder == null) {
            throw new NotFoundException("Cannot find product with id " + id);
        }

        boolean deleted = OrderEntity.deleteById(id);
        return deleted ? foundOrder : null;
    }
}
