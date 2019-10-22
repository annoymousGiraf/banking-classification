package transactions.query

import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
class TransactionListQuery(val transactions: TransactionLines) {

    fun getCurrentBalance(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime) : BigDecimal {
        val reversedTransaction = getReversedTransactions(accountId)

         return  transactions.transactionLines
            .asSequence()
            .filter { it.fromAccountId == accountId }
            .filter { it.createAt.isAfter(startDate) && it.createAt.isBefore(endDate) }
            .filter { !reversedTransaction.contains(it.transactionId) }
            .map{ it.amount }
            .fold(BigDecimal.ZERO, BigDecimal::minus)

    }

    private fun getReversedTransactions(accountId: String): List<String> {
        return transactions.transactionLines
            .filter { it.fromAccountId == accountId }
            .filter { it.transactionType == TransactionType.REVERSAL }
            .map { it.relatedTransaction }
    }
}
