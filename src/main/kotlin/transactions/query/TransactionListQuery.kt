package transactions.query

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import transactions.entity.RelativeBalance
import transactions.entity.TransactionLine
import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime



class TransactionListQuery(private val transactions: TransactionLines) {

    private val logger = KotlinLogging.logger {}

    fun getCurrentBalance(accountId: String, startDate: LocalDateTime, endDate: LocalDateTime)= runBlocking {
        logger.debug { "getCurrentBalance(accountId=$accountId,startDate=$startDate,endDate=$endDate)" }
        check(startDate <= endDate) { "start date $startDate is after end date $endDate" }
        logger.info { "Start Processing Balance for AccountId=$accountId" }

        val transactionForAccountInTimeFrame = getTransactionForTimeFrame(startDate, endDate,
                        getReversedTransactions(accountId))

        logger.info { "found ${transactionForAccountInTimeFrame.size} matching transactions" }

        val transactionPaid =
             async { getDebitTransaction(transactionForAccountInTimeFrame, accountId)}

        val transactionReceived =
            async {  getCreditTransactions(transactionForAccountInTimeFrame, accountId) }

        val allTransaction = transactionPaid.await() + transactionReceived.await()
        val balance = allTransaction
            .asSequence()
            .fold(BigDecimal.ZERO, BigDecimal::add)
        logger.info { "Finish calculating balance" }
        logger.debug { "Balance = $balance" }
        RelativeBalance(allTransaction.size, balance)
    }

    private fun getCreditTransactions(transactionForAccountInTimeFrame: List<TransactionLine>,
                                      accountId: String): List<BigDecimal> {
        logger.info { "getting credit transactions for account = $accountId" }
        return transactionForAccountInTimeFrame
            .filter { it.toAccountId == accountId }
            .map { it.amount }
    }

    private  fun getDebitTransaction(
        transactionForAccountInTimeFrame: List<TransactionLine>, accountId: String): List<BigDecimal> {
        logger.info { "getting debit transactions for account = $accountId" }
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
