package serruf.aptito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serruf.aptito.model.Transaction;
import serruf.aptito.service.TransactionService;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(path = "/order/{orderId}/pay", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pay(@PathVariable("orderId") Long orderId, @RequestBody Transaction transaction) {
        transactionService.processTransaction(orderId, transaction);
    }
}
