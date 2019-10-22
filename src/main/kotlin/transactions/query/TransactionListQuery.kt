package transactions.query

import transactions.entity.RelativeBalance
import transactions.entity.TransactionLine
import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionListQuery(val transactions: TransactionLines) {

    fun getCurrentBalance(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime) : RelativeBalance {

        val transactionForAccountInTimeFrame = getTransactionForTimeFrame(startDate, endDate,
                        getReversedTransactions(accountId))

        val transactionPaid =
             getDebitTransaction(transactionForAccountInTimeFrame, accountId)

        val transactionReceived =
            getCreditTransactions(transactionForAccountInTimeFrame, accountId)

        val allTransaction = transactionPaid + transactionReceived

        val balance = allTransaction
            .asSequence()
            .fold(BigDecimal.ZERO, BigDecimal::add)

        return RelativeBalance(allTransaction.size, balance)
    }

    private fun getCreditTransactions(
        transactionForAccountInTimeFrame: List<TransactionLine>, accountId: String): List<BigDecimal> {
        return transactionForAccountInTimeFrame
            .filter { it.toAccountId == accountId }
            .map { it.amount }
    }

    private fun getDebitTransaction(
        transactionForAccountInTimeFrame: List<TransactionLine>, accountId: String): List<BigDecimal> {
        return transactionForAccountInTimeFrame
            .filter { it.fromAccountId == accountId }
            .map { it.amount.negate() }
    }

    private fun getTransactionForTimeFrame(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        reversedTransaction: List<String>
    ): List<TransactionLine> {
        return transactions.transactionLines
            .filter { (it.createAt.isAfter(startDate) && it.createAt.isBefore(endDate)) ||
                    (it.createAt == startDate || it.createAt == endDate) }
            .filter { !reversedTransaction.contains(it.transactionId) }
    }

    private fun getReversedTransactions(accountId: String): List<String> {
        return transactions.transactionLines
            .filter { it.fromAccountId == accountId }
            .filter { it.transactionType == TransactionType.REVERSAL }
            .map { it.relatedTransaction }
    }
}
