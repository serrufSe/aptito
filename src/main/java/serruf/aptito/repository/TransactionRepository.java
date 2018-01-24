package serruf.aptito.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import serruf.aptito.model.Transaction;

import javax.persistence.TemporalType;
import java.util.Date;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select coalesce(sum(t.amount), 0) from Transaction t where (t.order.id = :orderId)")
    public int getOrderSum(@Param("orderId") Long orderId);

    @Query("select o.amount - coalesce(sum(t.amount), 0) from Order o join o.transactions t where (o.id = :orderId)")
    public int getRestSum(@Param("orderId") Long orderId);

    @Query("select coalesce(sum(t.amount), 0) from Transaction t where t.createdAt BETWEEN :startDate AND :endDate")
    public int  getSumByPeriod(@Param("startDate") @Temporal(TemporalType.TIMESTAMP) Date startDate,
                               @Param("endDate") @Temporal(TemporalType.TIMESTAMP) Date endDate);
}
