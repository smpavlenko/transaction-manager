package com.pavlenko.manager.service;

import com.pavlenko.manager.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private Map<Long, Transaction> transactionMap = new HashMap<>();
    private Map<Long, Double> sumAmountMap = new HashMap<>();

    @Override
    public void createTransaction(long transactionId, Transaction transaction) {
        if (transaction.getParentId() != 0 && !transactionMap.containsKey(transaction.getParentId())) {
            throw new IllegalArgumentException(String.format("No parent transaction found with id=%s", transaction.getParentId()));
        }
        double amount = transaction.getAmount();
        transactionMap.put(transactionId, transaction);
        sumAmountMap.put(transactionId, amount);

        propagateAmountToParentTransactions(transaction.getParentId(), amount);
    }

    private void propagateAmountToParentTransactions(long id, double amount) {
        while (id != 0) {
            sumAmountMap.put(id, sumAmountMap.get(id) + amount);
            id = transactionMap.get(id).getParentId();
        }
    }

    @Override
    public Transaction getTransactionById(long transactionId) {
        return transactionMap.get(transactionId);
    }

    @Override
    public List<Long> getTransactionIdsByType(String transactionType) {
        List<Long> result = new ArrayList<>();

        for (Map.Entry<Long, Transaction> transactionEntry : transactionMap.entrySet()) {
            if (transactionType.equals(transactionEntry.getValue().getType())) {
                result.add(transactionEntry.getKey());
            }
        }
        return result;
    }

    @Override
    public double getTransactionSumById(long transactionId) {
        if (!transactionMap.containsKey(transactionId)) {
            throw new IllegalArgumentException(String.format("No transaction found with id=%s", transactionId));
        }

        return sumAmountMap.get(transactionId);
    }
}
