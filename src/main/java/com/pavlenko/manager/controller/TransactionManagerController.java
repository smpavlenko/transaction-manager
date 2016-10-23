package com.pavlenko.manager.controller;

import com.pavlenko.manager.model.TotalAmount;
import com.pavlenko.manager.model.Transaction;
import com.pavlenko.manager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Main controller which includes main endpoints for maintaining transactions
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
@RestController
@RequestMapping("/transactionservice")
public class TransactionManagerController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> createOrReplaceTransaction(@PathVariable("transaction_id") long transactionId, @RequestBody Transaction transaction) {
        try {
            transactionService.createOrReplaceTransaction(transactionId, transaction);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> getTransaction(@PathVariable("transaction_id") long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction == null) {
            return new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<Long>> getTransactionIdsByType(@PathVariable("type") String transactionType) {
        List<Long> list = transactionService.getTransactionIdsByType(transactionType);
        return new ResponseEntity<List<Long>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/sum/{transaction_id}", method = RequestMethod.GET)
    public ResponseEntity<TotalAmount> getTransactionsSum(@PathVariable("transaction_id") Long transactionId) {
        double sum;
        try {
            sum = transactionService.getTransactionSumById(transactionId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<TotalAmount>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<TotalAmount>(new TotalAmount(sum), HttpStatus.OK);
    }


}
