package com.pavlenko.manager.service;

import com.pavlenko.manager.model.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link TransactionService}
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
public class TransactionServiceImplTest {

    private TransactionService transactionService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        transactionService = new TransactionServiceImpl();

        transactionService.createOrReplaceTransaction(1L, createTransaction("Type1", 1.001, 0));
        transactionService.createOrReplaceTransaction(2L, createTransaction("Type1", 1.002, 0));
        transactionService.createOrReplaceTransaction(3L, createTransaction("Type2", 1.003, 1));
        transactionService.createOrReplaceTransaction(4L, createTransaction("Type3", 1.004, 3));
    }

    @Test
    public void testCreateTransactionParentNotFound() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No parent transaction found with id=100");
        transactionService.createOrReplaceTransaction(5L, createTransaction("Type2", 1.003, 100L));
    }

    @Test
    public void testGetTransactionByIdNotFound() throws Exception {
        assertThat(transactionService.getTransactionById(1000L), nullValue());
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction resultTransaction = transactionService.getTransactionById(1L);

        assertThat(resultTransaction.getType(), equalTo("Type1"));
        assertThat(resultTransaction.getAmount(), equalTo(1.001));
    }

    @Test
    public void testGetTransactionIdsByTypeNotFound() throws Exception {
        List<Long> resultList = transactionService.getTransactionIdsByType("NotFound");

        assertThat(resultList, hasSize(0));
    }

    @Test
    public void testGetTransactionIdsByType() throws Exception {
        List<Long> resultList = transactionService.getTransactionIdsByType("Type1");

        assertThat(resultList, containsInAnyOrder(1L, 2L));
    }

    @Test
    public void testGetTransactionSumById() throws Exception {
        double result = transactionService.getTransactionSumById(2);

        assertThat(result, equalTo(1.002));
    }

    @Test
    public void testGetTransactionSumByIdWithChildren() throws Exception {
        double result = transactionService.getTransactionSumById(1);

        assertThat(result, equalTo(1.001 + 1.003 + 1.004));
    }

    @Test
    public void testGetTransactionSumByIdWithChildrenTransactionNotFound() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No transaction found with id=100");
        transactionService.getTransactionSumById(100L);
    }

    private Transaction createTransaction(String type, double amount, long parentId) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setParentId(parentId);
        return transaction;
    }

}
