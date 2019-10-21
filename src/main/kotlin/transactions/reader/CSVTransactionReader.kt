package transactions.reader

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import transactions.entity.TransactionLine
import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import transactions.reader.CSVTransactionReader.Columns.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CSVTransactionReader(fileLines : String) : TransactionInputReader {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") //TODO: move to property

    private val csvData : List<Map<String, String>> = csvReader().readAllWithHeader(fileLines)

    private fun errorReading(colName :String ): Nothing = error("missing column $colName")

    override fun read(): TransactionLines {

        return TransactionLines(
            csvData.map {
            TransactionLine(
                it[TRANSACTION_ID.col] ?: errorReading(TRANSACTION_ID.col),
                it[FROM_ACCOUNT_ID.col] ?: errorReading(FROM_ACCOUNT_ID.col),
                it[TO_ACCOUNT_ID.col] ?: errorReading(FROM_ACCOUNT_ID.col),
                parseDate(it[CREATED_AT.col] ?: errorReading(CREATED_AT.col)),
                BigDecimal( it[AMOUNT.col]  ?: errorReading(AMOUNT.col)),
                parseTransactionType(it[TRANSACTION_TYPE.col] ?: errorReading(TRANSACTION_TYPE.col))

           )
        })
    }

    private fun parseTransactionType(transactionType : String) =
        TransactionType.valueOf(transactionType)

    private fun parseDate(dateAsString: String) =
        LocalDateTime.parse(dateAsString , formatter)


    enum class Columns(val col : String) {
        TRANSACTION_ID("transactionId") ,
        FROM_ACCOUNT_ID("fromAccountId"),
        TO_ACCOUNT_ID("toAccountId"),
        CREATED_AT("createdAt"),
        AMOUNT("amount"),
        TRANSACTION_TYPE("transactionType"),
        RELATED_TRANSACTION("relatedTransaction")
    }
}
