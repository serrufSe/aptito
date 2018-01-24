package serruf.aptito.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import serruf.aptito.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select coalesce(sum(t.amount), 0) from Transaction t where (t.order.id = :orderId)")
    public int getOrderSum(@Param("orderId") Long orderId);
}
