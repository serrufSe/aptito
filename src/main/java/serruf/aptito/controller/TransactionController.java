package serruf.aptito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serruf.aptito.model.Transaction;
import serruf.aptito.repository.TransactionRepository;
import serruf.aptito.service.TransactionService;

import java.util.Date;


@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping(path = "/order/{orderId}/pay", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pay(@PathVariable("orderId") Long orderId, @RequestBody Transaction transaction) {
        transactionService.processTransaction(orderId, transaction);
    }

    @RequestMapping(path = "/transactions")
    public Iterable<Transaction> transactions() {
        return transactionRepository.findAll();
    }

    @RequestMapping(path = "/order/{orderId}/rest")
    public int rest(@PathVariable("orderId") Long orderId) {
        return transactionRepository.getRestSum(orderId);
    }

    @RequestMapping(path = "/statistics")
    public int statistics(@RequestParam(name = "statDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date statDate,
                          @RequestParam(name = "endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        return transactionRepository.getSumByPeriod(statDate, endDate);
    }
}
