package transactions.reader

import transactions.entity.TransactionLine
import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import transactions.reader.CSVTransactionReader.Columns.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CSVTransactionReader : TransactionInputReader {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") //TODO: move to property


    override fun read(transactionLines: Array<String>): TransactionLines {
        return TransactionLines(transactionLines.map {
            val line = it.split(",")
            TransactionLine(line[TRANSACTION_ID.ordinal], line[FROM_ACCOUNT_ID.ordinal], line[TO_ACCOUNT_ID.ordinal],
            LocalDateTime.parse(line[CREATED_AT.ordinal]  , formatter), BigDecimal( line[AMOUNT.ordinal]),
                TransactionType.valueOf(line[TRANSACTION_TYPE.ordinal]))
        })
    }

/*
val transactionId : String, val fromAccountId : String, val toAccountId : String,
                           val createAt : LocalDate, val amount: BigDecimal, val transactionType : TransactionType,
                           val relatedTransaction : String = ""
 */
    enum class Columns {
        TRANSACTION_ID,
        FROM_ACCOUNT_ID,
        TO_ACCOUNT_ID,
        CREATED_AT,
        AMOUNT,
        TRANSACTION_TYPE,
        RELATED_TRANSACTION
    }
}
