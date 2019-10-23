# Banking Classification Task

#### Table of Contents  
1. [Run And Build](#runAndBuild)  
2. [Further Work](#further)  
3. [Basic Diagram](#diagram)  
4. [Assumptions](#assumptions)  


The idea behind the solution is to be lean and elegant, easy to change as we are 
in the software industry requirements are chaining fast and we need to be able to adjust fast
for example the task requires to support CSV but in just few lines of code we cam add support to JSON format by adding a Parser and adding 
type to the Factory. as well as a contract in the form of Interface for InputReader,
most of the types are generic as it can be to support multiple implementations such as the use
of Collection rather then just List. the use of Data Classes eases the movement and storage of the parsed data. Excretion function is used to 
have a syntactic sugar approach of changing string dates to Date data structure but also gives us one place that we can manage the
Date format, which we can source out to a property file.

in the Transaction Query Service layer Coroutine was used on some calculation in order to allow scaling in larger capacities. 
such as if we had a Data store in the middle.  

the use of BigDecimal for money calculation was very important to me in order to not lose any cent on the dollar 
due to data type behavior.   


<a name="runAndBuild"/>

### Local Env And Building

* Using `Docker` and `docker-compose`
    * `docker-compose run build`
    * `docker-compose build release`
    * `docker run banking-classification:latest "<sample from the files in sample folder>" "<account_id>" "<strart_date>" "<end_date>"`
    
* Using docker with automate sample run
    * `./dev-env/build-and-run-sample-with-docker.sh`


* Using Java
    * `./dev-env/run-with-your-args.sh <accountid> <start_date> <end_date>` sample will be take from app folder and be used from the script
    * build your own and use it `./gradlew clean jar` on the `build` folder under `libs` the jar will be build run it with all the arguments 
    `java -jar banking-classification-1.0.jar <csv_file> <account_id> <start_date> <end_date>`

* running tests
    * using gradle `./gradlew clean test`
    * using docker `docker-compose run test`
    * using intelij or any other IDE
<a name="further"/>   
 
### Further Work
in order to not over engineering the task i avoided demonstrated possible features such as
1. exporting the date format to a property file
2. running regex on date input
3. supporting json file - though it is super easy with the given design
4. adding test coverage tool.
5. Improve basic logger.

<a name="diagram"/>

### Basic Diagram

```
                                                            +--------------------+
                                                            |    Current Balance |
                                                            |                    |
                                                            +---------+----------+
                                                                      ^
                                                                      |
                                                                      |
                                           Input File , Account , Star| date, End date
                                                             +        |
                                                             |        |
                                                             |        |
                                                             |        |
                                                             |        |
                                                             |        |
+--------------------+                                       |        |
|                    | TransactionList      +----------------v--------+------+
|  Transaction Query +<---------------------+           Application          |
|  Service           | Dates and Account    |                                |
|                    |                      |                                |
|                    | current balance for  |                                |
|                    +---------------------->                                |
+--------------------+ account              +---------------+-------+--------+
                                                            |       ^
                                                            |       |
                                                            |       | InputReader - read()
                                                            |       |
                                                            |       |
      +---------------------+                               v       |
      |                     |              +----------------+-------+-----------+            +---------------------+
      |  JsonInputReader -  |              |                                    |            |  CSVInputReader     |
      |  Future Development |              |         InputReaderFactory         |            |                     |
      |                     +<-------------+                                    +----------->+                     |
      |                     |              |                                    |            |                     |
      |                     |              |                                    |            |                     |
      |                     |              +---------------+--------------------+            +---------------------+
      +---------------------+                              |
                                                           |
                                                           |
                                                           |
                                              +------------+---------------+
                                              |   Unsupported Type         |
                                              |                            |
                                              |                            |
                                              |                            |
                                              |                            |
                                              +----------------------------+

```

<a name="assumptions"/>

### Assumptions

* On the Example in the PDF the CSV file is missing a `,` if it is a payment i am assuming that the CSV file contains trailing comma any way


##### a Fixed CSV will look like:

```
fromAccountId, toAccountId, createdAt, amount, transactionType,relatedTransaction
TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT,
TX10002, ACC334455, ACC998877, 20/10/2018 17:33:43, 10.50, PAYMENT,
TX10003, ACC998877, ACC778899, 20/10/2018 18:00:00, 5.00, PAYMENT,
TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL,TX10002 
TX10005, ACC334455, ACC778899, 21/10/2018 09:30:00, 7.25,PAYMENT,
``` 


* Amount scale is 2 , means two zeros after the `.` for example `7.25`

* All transaction are on the same time zone