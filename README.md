A Simple Banking System

* This project is a simple banking system built with Java and Spring Boot

   (Java 17 , maven 3.6)

* Some outstanding features: 
- Thread-safe concurrent collections
- UUID generation for secure IDs
- BigDecimal for financial precision
- Proper exception handling
- Business validation:  minimum amount checks or transfer limits

+ Design Choices : 

 - No database is required. Data is stored in thread-safe collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`) within singleton Spring components (`@Repository`).
-  "Layered Architecture": The application is structured into layers: Controller (API), Service (Business Logic), and Repository (Data Access).
   in service layer i use CQRS (Command Query Responsibility Segregation) : CQRS pattern 
             Commands Services : change the state of the application ( AccountCommandService )
             Queries Services  : read data (AccountQueryService) 
-  This system uses UUID (Universally Unique Identifier) ​​to ensure uniqueness for important entity such as Account, Customer, Transaction and system configurations . 
UUID is a randomly generated character string that helps avoid duplication between records.
-  In additional , basic thread safety is implemented using locks "ReentrantLock" on domain objects , using collections in repository.


The application will start and be available at http://localhost:8081
 Test with Postman


* Note: You will need to replace {accountId} and {fromAccountId} with the actual account ID returned when you create an account

For example :
this api : http://localhost:8081/api/accounts/{accountId}/balance 
  if you want to check this id = e70d88f2-d70c-4a46-909e-4fc1d9d6312d
you must replace : http://localhost:8081/api/accounts/e70d88f2-d70c-4a46-909e-4fc1d9d6312d/balance


### Customer Operations

**1. Create a new account**
*   **Method**: `POST`
*   **URL**: `http://localhost:8081/api/accounts`
*   **Body** (raw, JSON):
    ```json
    {
        "customerName": "Long Vo",
        "initialDeposit": 100.00
    }
    ```
*   **Response**: The full account object, including the new `id`. **Copy this `id` for other requests.**

**2. Get account balance**
*   **Method**: `GET`
*   **URL**: `http://localhost:8081/api/accounts/{accountId}/balance`

**3. Get account transactions**
*   **Method**: `GET`
*   **URL**: `http://localhost:8081/api/accounts/{accountId}/transactions`

**4. Deposit funds**
*   **Method**: `POST`
*   **URL**: `http://localhost:8081/api/accounts/{accountId}/deposit`
*   **Body** (raw, JSON):
    ```json
    {
        "amount": 50.00
    }
    ```

**5. Withdraw funds**
*   **Method**: `POST`
*   **URL**: `http://localhost:8081/api/accounts/{accountId}/withdraw`
*   **Body** (raw, JSON):
    ```json
    {
        "amount": 25.00
    }
    ```

**6. Transfer funds**
*   **Method**: `POST`
*   **URL**: `http://localhost:8081/api/accounts/{fromAccountId}/transfer`
*   **Body** (raw, JSON):
    ```json
    {
        "toAccountId": "_id_of_the_destination_account_", 
        "amount": 75.00
    }
    ```

---

### Bank Manager Operations

**1. Get total bank balance**
*   **Method**: `GET`
*   **URL**: `http://localhost:8081/api/manager/total-balance`

**2. Get all transactions**
*   **Method**: `GET`
*   **URL**: `http://localhost:8081/api/manager/transactions`

**3. Get all accounts information**
*   **Method**: `GET`
*   **URL**: `http://localhost:8081/api/manager/accounts` 
