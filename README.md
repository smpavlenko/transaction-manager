## RESTful Transaction Manager

A RESTful web service that stores some transactions (in memory) and returns information about those transactions.
The transactions to be stored have a type and an amount. The service supports returning all transactions of a type. Also, transactions can be linked to each other (using a "parent_id") and the total amount involved for all transactions linked to a particular transaction can also be returned.

Project is compilable with Java 1.7 or later. The result war file is deployable on application server(tested on Apache Tomcat 9.0.0.M10).


### The api spec looks like the following:
* `PUT /transactionservice/transaction/$transaction_id`
- Body: { "amount":double,"type":string,"parent_id":long }
- Has O(1) best case complexity (when input transaction has no parent it)
- Has O(n) worst case complexity (when input and all stored transactions are related to each other)

* `GET /transactionservice/transaction/$transaction_id`
- Returns: { "amount":double,"type":string,"parent_id":long }
- Has O(1) complexity

* `GET /transactionservice/types/$type`
- Returns: [ long, long, .... ]
- Has O(n) complexity

* `GET /transactionservice/sum/$transaction_id`
- Returns: { "sum", double }
- Has O(1) complexity


### Examples
```java
PUT /transactionservice/transaction/10 { "amount": 5000, "type":"cars" } => { "status": "ok" }
```

```java
PUT /transactionservice/transaction/11 { "amount": 10000, "type": "shopping", "parent_id": 10 } => { "status": "ok" }
```

```java
GET /transactionservice/types/cars => [10]
```

```java
GET /transactionservice/sum/10 => {"sum":15000}
```

```java
GET /transactionservice/sum/11 => {"sum":10000}
```