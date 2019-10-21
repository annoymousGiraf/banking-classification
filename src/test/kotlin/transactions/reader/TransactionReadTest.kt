package transactions.reader


import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
            val csvLine = arrayOf(oneTransactionLineCSVFormat)
            val inputReader: TransactionInputReader = InputReaderFactory.of("csv")

            val transactionLine = TransactionLine("TX10001", "ACC334455",
                "ACC778899", LocalDateTime.parse("20/10/2018 12:47:55" , formatter),
                BigDecimal("25.00"), TransactionType.PAYMENT )

            val expectedTransactionLines = TransactionLines(listOf(transactionLine))

            //When
            val transactions = inputReader.read(csvLine)
            //Then
            assertThat(transactions, equalTo(expectedTransactionLines))
        }
    }

}