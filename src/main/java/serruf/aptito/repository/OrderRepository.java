package serruf.aptito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import serruf.aptito.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
