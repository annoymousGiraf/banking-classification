# Banking-Classification Task

#### Table of Contents  
[Run And Build](#runAndBuild)  
[Further Work](#further)  
[Basic Diagram](#diagram)  
[Assumptions](#assumptions)  


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