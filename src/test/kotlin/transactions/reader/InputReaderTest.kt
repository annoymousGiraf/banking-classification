package transactions.reader

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test




class InputReaderTest {

    @Test
    fun `input ReaderFactory should produce CSV reader instance`() {
        //Given
       val inputType = "csv"

        //When
        val inputReader = InputReaderFactory.of(inputType)

        //Then
        assertEquals(inputReader.javaClass, CSVTransactionReader().javaClass)

    }

    @Test
    fun `unsupported InputReader Type should throw exception`() {
        //Given
        val inputType = "xml"

        //Whet
        Assertions.assertThrows(IllegalArgumentException::class.java) {  InputReaderFactory.of(inputType) }



    }


}