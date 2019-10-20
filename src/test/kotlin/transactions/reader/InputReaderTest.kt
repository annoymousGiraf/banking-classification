package transactions.reader

import junit.framework.TestCase.assertEquals
import org.junit.Test


class InputReaderTest {

    @Test
    fun inputReaderFactoryCSVTest() {
        //Given
       val inputType = "csv"

        //When
        val inputReader = InputReaderFactory.of(inputType)

        //Then
        assertEquals(inputReader.javaClass, CSVTransactionReader().javaClass)

    }
}