package transactions.query

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import transactions.data.completeCSVSample
import transactions.reader.CSVTransactionReader
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QueryTransactionListTest {


    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    private val TransactionLines = CSVTransactionReader(completeCSVSample).read()


    @Test
    fun `should be able to get current balance for account`() {
        //Given
        val startDate = LocalDateTime.parse("20/10/2018 12:00:00", formatter)
        val endDate = LocalDateTime.parse("20/10/2018 19:00:00", formatter)
        val accountId = "ACC334455"
        val transactionListQuery = TransactionListQuery(TransactionLines)
        val expectedBalance = BigDecimal(-25.00).setScale(2)

        //When
        val currentBalance = transactionListQuery.getCurrentBalance(accountId,startDate,endDate)

        //Then
        assertThat(currentBalance, equalTo(expectedBalance))
    }

}