package transactions.reader

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InputReaderTest {


    val testFileName = "test.csv"

    @BeforeAll
    fun setup(){
        File(testFileName).writeText("1,2,3,4,5")
    }

    @AfterAll
    fun tearDown(){
        File(testFileName).delete()
    }

    @Test
    fun `input ReaderFactory should produce CSV reader instance`() {
        //Given

        //When
        val inputReader = InputReaderFactory.of(File(testFileName))

        //Then
        assertEquals(inputReader.javaClass, CSVTransactionReader("").javaClass)

    }

    @Test
    fun `unsupported InputReader Type should throw exception`() {
        //Given
        val filePath = "file.xml"

        //When
        Assertions.assertThrows(IllegalArgumentException::class.java) {  InputReaderFactory.of(File(filePath)) }



    }


}