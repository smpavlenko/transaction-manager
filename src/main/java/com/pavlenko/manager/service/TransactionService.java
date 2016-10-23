package com.pavlenko.manager.service;

import com.pavlenko.manager.model.Transaction;

import java.util.List;

/**
 * Transaction service which provides main services for maintaining transactions
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
public interface TransactionService {

    /**
     * Stores transaction by id
     *
     * @param transactionId transaction id
     * @param transaction   transaction object
     */
    void createTransaction(long transactionId, Transaction transaction);

    /**
     * Gets transaction by transaction id
     *
     * @param transactionId transaction id
     * @return transaction object
     */
    Transaction getTransactionById(long transactionId);

    /**
     * Gets a list of transaction Ids by specific transaction type
     *
     * @param transactionType transaction type
     * @return a list of transaction ids
     */
    List<Long> getTransactionIdsByType(String transactionType);

    /**
     * Gets sum of transaction and all child transactions amounts
     *
     * @param transactionId transaction id
     * @return a sum of amounts
     */
    double getTransactionSumById(long transactionId);
}
