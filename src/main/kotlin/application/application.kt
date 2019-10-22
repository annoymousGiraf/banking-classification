package application

import transactions.query.TransactionListQuery
import transactions.reader.InputReaderFactory
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") //TODO: move to property

fun main(args : Array<String>)  {
   check(args.size >= 4) {
       println("Usage: <input_file> <account_id> <start_date> <end_date>")
       exitProcess(1)
   }
    val filePath = args[0]
    val accountId = args[1]
    val startDate = LocalDateTime.parse(args[2], formatter)
    val endDate =  LocalDateTime.parse(args[3], formatter)

    val inputReader = InputReaderFactory.of(File(filePath))

    val transactionListQuery = TransactionListQuery(inputReader.read())

    val balance = transactionListQuery.getCurrentBalance(accountId,startDate,endDate)

    println(balance)
}