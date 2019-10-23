package application

import extention.toDate
import transactions.query.TransactionListQuery
import transactions.reader.InputReaderFactory
import java.io.File
import kotlin.system.exitProcess


fun main(args : Array<String>)  {
   check(args.size == 4) {
       println("Usage: <input_file> <account_id> <start_date> <end_date>")
       exitProcess(1)
   }
    val filePath = args[0]
    val accountId = args[1]
    val startDate = args[2].toDate()
    val endDate =  args[3].toDate()

    val inputReader = InputReaderFactory.of(File(filePath))

    val transactionListQuery = TransactionListQuery(inputReader.read())

    val balance = transactionListQuery.getCurrentBalance(accountId,startDate,endDate)

    println(balance)
    exitProcess(0)
}