package com.pavlenko.manager.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import com.pavlenko.manager.model.Transaction;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * RESTful unit tests
 *
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
@Ignore
public class TransactionManagerControllerTest {
    private static RequestSpecification spec;

    public static final String REST_SERVICE_URI = "http://localhost:8080/RESTfulTransactionManager";

    @BeforeClass
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(REST_SERVICE_URI)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();

        given()
                .spec(spec)
                .body(createTransaction("Type1", 1001, 0))
                .when()
                .put("/transactionservice/transaction/1")
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .spec(spec)
                .body(createTransaction("Type1", 1002, 0))
                .when()
                .put("/transactionservice/transaction/2")
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .spec(spec)
                .body(createTransaction("Type2", 1003, 1))
                .when()
                .put("/transactionservice/transaction/3")
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .spec(spec)
                .body(createTransaction("Type3", 1004, 3))
                .when()
                .put("/transactionservice/transaction/4")
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void testCreateTransactionParentNotFound() throws Exception {
        given()
                .spec(spec)
                .body(createTransaction("Type2", 1003, 100L))
                .when()
                .put("/transactionservice/transaction/5")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testGetTransactionByIdNotFound() throws Exception {
        given()
                .spec(spec)
                .when()
                .get("/transactionservice/transaction/1000")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction resultTransaction = given()
                .spec(spec)
                .when()
                .get("/transactionservice/transaction/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(Transaction.class);

        assertThat(resultTransaction.getType(), equalTo("Type1"));
        assertThat(resultTransaction.getAmount(), equalTo(1001.0));
    }

    @Test
    public void testGetTransactionIdsByTypeNotFound() throws Exception {
        List<Long> resultList = given()
                .spec(spec)
                .when()
                .get("/transactionservice/types/NotFound")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(List.class);

        assertThat(resultList, hasSize(0));
    }

    @Test
    public void testGetTransactionIdsByType() throws Exception {
        List<Long> resultList = given()
                .spec(spec)
                .when()
                .get("/transactionservice/types/Type1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().as(List.class);

        assertTrue(resultList.contains(1));
        assertTrue(resultList.contains(2));
    }

    @Test
    public void testGetTransactionSumById() throws Exception {
        given()
                .spec(spec)
                .when()
                .get("/transactionservice/sum/2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("{\"sum\",1002.0}"));
    }

    @Test
    public void testGetTransactionSumByIdWithChildren() throws Exception {
        given()
                .spec(spec)
                .when()
                .get("/transactionservice/sum/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo("{\"sum\",3008.0}"));
    }

    @Test
    public void testGetTransactionSumByIdWithChildrenTransactionNotFound() throws Exception {
        given()
                .spec(spec)
                .when()
                .get("/transactionservice/sum/100")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private static Transaction createTransaction(String type, double amount, long parentId) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setParentId(parentId);
        return transaction;
    }

}
