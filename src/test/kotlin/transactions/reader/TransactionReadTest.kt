package transactions.reader


import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import transactions.data.multipleTransactionLineCSVFormatWithReversal
import transactions.data.oneTransactionLineCSVFormat
import transactions.entity.TransactionLine
import transactions.entity.TransactionLines
import transactions.entity.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionReadTest {


    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    @Nested
    inner class CSVFileRead {
        @Test
        fun `should read one simple CSV transaction line`() {
            //Given
            val inputReader: TransactionInputReader = CSVTransactionReader(oneTransactionLineCSVFormat)

            val transactionLine = TransactionLine("TX10001", "ACC334455",
                "ACC778899", LocalDateTime.parse("20/10/2018 12:47:55" , formatter),
                BigDecimal("25.00"), TransactionType.PAYMENT )

            val expectedTransactionLines = TransactionLines(listOf(transactionLine))

            //When
            val transactions = inputReader.read()

            //Then
            assertThat(transactions, equalTo(expectedTransactionLines))
        }

        @Test
        fun `should read multiple CSV lines`() {
            //Given
            val inputReader: TransactionInputReader = CSVTransactionReader(multipleTransactionLineCSVFormatWithReversal)

            val transactionLine1 = TransactionLine("TX10001", "ACC334455",
                "ACC778899", LocalDateTime.parse("20/10/2018 12:47:55" , formatter),
                BigDecimal("25.00"), TransactionType.PAYMENT )

            val transactionLine2 = TransactionLine("TX10004", "ACC334455",
                "ACC998877", LocalDateTime.parse("20/10/2018 19:45:00" , formatter),
                BigDecimal("10.50"), TransactionType.REVERSAL,"TX10001" )

            val expectedTransactionLines = TransactionLines(listOf(transactionLine1,transactionLine2))

            //When
            val transactions = inputReader.read()

            //Then
            assertThat(transactions, equalTo(expectedTransactionLines))
        }




    }

}