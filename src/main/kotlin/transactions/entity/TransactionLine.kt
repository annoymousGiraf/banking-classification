package transactions.entity

import java.math.BigDecimal
import java.time.LocalDateTime


data class TransactionLine(val transactionId : String, val fromAccountId : String, val toAccountId : String,
                           val createAt : LocalDateTime, val amount: BigDecimal, val transactionType : TransactionType,
                           val relatedTransaction : String = "")


enum class TransactionType {
    PAYMENT,
    REVERSAL
}