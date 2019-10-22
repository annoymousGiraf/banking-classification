# banking-classification


### Assumptions

* On the Example in the PDF the CSV file is missing a `,` if it is a payment i am assuming that the CSV file contains trailing comma any way


##### a Fixed CSV will look like:

``
fromAccountId, toAccountId, createdAt, amount, transactionType,relatedTransaction
TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT,
TX10002, ACC334455, ACC998877, 20/10/2018 17:33:43, 10.50, PAYMENT,
TX10003, ACC998877, ACC778899, 20/10/2018 18:00:00, 5.00, PAYMENT,
TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL,TX10002 
TX10005, ACC334455, ACC778899, 21/10/2018 09:30:00, 7.25,PAYMENT,
`` 

* Amount scale is 2 , means two zeros after the `.` for example `7.25`

* All transaction are on the same time zone