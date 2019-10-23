package transactions.query

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import extention.toDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import transactions.data.completeCSVSample
import transactions.entity.RelativeBalance
import transactions.reader.CSVTransactionReader
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryTransactionListTest {

    private val transactionLines = CSVTransactionReader(completeCSVSample).read()


    @Nested
    inner class CSVReadingAndQuery {


        @Test
        fun `should be able to get current balance for account with reversal`() {
            //Given
            val startDate ="20/10/2018 12:00:00".toDate()
            val endDate = "20/10/2018 19:00:00".toDate()
            val accountId = "ACC334455"
            val transactionListQuery = TransactionListQuery(transactionLines)
            val expectedBalance = RelativeBalance(1, BigDecimal(-25.00).setScale(2))

            //When
            val currentBalance = transactionListQuery.getCurrentBalance(accountId, startDate, endDate)

            //Then
            assertThat(currentBalance, equalTo(expectedBalance))
        }

        @Test
        fun `should be able to get current balance for account in positive cash flow`() {
            //Given
            val startDate = "20/10/2018 12:00:00".toDate()
            val endDate ="20/10/2018 19:00:00".toDate()
            val accountId = "ACC778899"
            val transactionListQuery = TransactionListQuery(transactionLines)
            val expectedBalance = RelativeBalance(2, BigDecimal(30.00).setScale(2))

            //When
            val currentBalance = transactionListQuery.getCurrentBalance(accountId, startDate, endDate)

            //Then
            assertThat(currentBalance, equalTo(expectedBalance))
        }

        @Test
        fun `should be in zero balance`() {
            //Given
            val startDate = "21/10/2018 20:00:00".toDate()
            val endDate = "21/10/2018 21:00:00".toDate()
            val accountId = "ACC711223"
            val transactionListQuery = TransactionListQuery(transactionLines)
            val expectedBalance = RelativeBalance(0, BigDecimal(0))

            //When
            val currentBalance = transactionListQuery.getCurrentBalance(accountId, startDate, endDate)

            //Then
            assertThat(currentBalance, equalTo(expectedBalance))
        }

        @Test
        fun `transaction should be on the edge of the time frame`() {
            //Given
            val startDate = "21/10/2018 20:00:00".toDate()
            val endDate = "21/10/2018 21:00:00".toDate()
            val accountId = "ACC555666"
            val transactionListQuery = TransactionListQuery(transactionLines)
            val expectedBalance = RelativeBalance(2, BigDecimal(-20.00).setScale(2))

            //When
            val currentBalance = transactionListQuery.getCurrentBalance(accountId, startDate, endDate)

            //Then
            assertThat(currentBalance, equalTo(expectedBalance))
        }
    }

    @Nested
    inner class InvalidInput {

        @Test
        fun `start date should not be after than end date`(){
            //Given
            val endDate = "21/10/2018 19:00:00".toDate()
            val startDate  ="21/10/2018 22:00:00".toDate()
            val accountId = "ACC555666"
            val transactionListQuery = TransactionListQuery(transactionLines)

            //When
            Assertions.assertThrows(IllegalStateException::class.java) {  transactionListQuery.getCurrentBalance(accountId, startDate, endDate) }

        }
    }

}

