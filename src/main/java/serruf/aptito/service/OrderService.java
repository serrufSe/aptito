package serruf.aptito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import serruf.aptito.exception.OverpaymentError;
import serruf.aptito.model.Order;
import serruf.aptito.repository.OrderRepository;
import serruf.aptito.repository.TransactionRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateOrder(Order order) {
        int payedSum = transactionRepository.getOrderSum(order.getId());

        if (payedSum > order.getAmount()) {
            throw new OverpaymentError();
        } else if(payedSum == order.getAmount()) {
            order.setCompleted(true);
        } else {
            order.setCompleted(false);
        }

        orderRepository.save(order);
    }
}
