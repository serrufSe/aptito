package serruf.aptito.specification;

import org.springframework.data.jpa.domain.Specification;
import serruf.aptito.model.Order;

public class OrderSpecification {

    public static Specification<Order> completedQuery(Boolean completed) {
        return (root, query, cb) -> cb.equal(root.get("completed"), completed);
    }

    public static Specification<Order> nameQuery(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%"+name+"%");
    }
}
