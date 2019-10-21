package transactions.reader


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import transactions.data.oneTransactionLineCSVFormat
import transactions.entity.TransactionLine
import transactions.entity.TransactionLines

class TransactionReadCSVTest {



    @Test
    fun simpleReadCSVLineTest() {
        //Given
        val csvLine = arrayOf( oneTransactionLineCSVFormat )
        val inputReader : TransactionInputReader = InputReaderFactory.of("csv")
        val transactionLine = TransactionLine("TX10001")
        val expectedTransactionLines = TransactionLines( listOf(transactionLine) )

        //When
        val transactions = inputReader.read(csvLine)

        //Then
        assertEquals(transactions , expectedTransactionLines)
   }

}