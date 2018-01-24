package serruf.aptito.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import serruf.aptito.exception.OverpaymentError;
import serruf.aptito.model.Order;
import serruf.aptito.model.Transaction;
import serruf.aptito.repository.OrderRepository;
import serruf.aptito.repository.TransactionRepository;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class.getName());

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processTransaction(Long orderId, Transaction transaction) {
        Order order = orderRepository.getOne(orderId);

        transaction.setOrder(order);

        log.info("Start transaction " + transaction);

        if (order.getComplete()) {
            throw new OverpaymentError();
        }

        int needToPay = order.getAmount() - transactionRepository.getOrderSum(orderId);

        if (transaction.getAmount() > needToPay) {
            throw new OverpaymentError();
        } else {
            transactionRepository.save(transaction);

            log.info("Finish transaction " + transaction);

            if (transaction.getAmount() == needToPay) {
                order.setCompleted(true);

                log.info("Complete order " + order.getId());
                orderRepository.save(order);
            }
        }
    }
}
