package extention

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StringExtentionTest {

    @Test
    fun `string should be transformed to date`() {
        //Given
        val stringAsDate = "20/10/2018 12:47:55"
        val expectedDate = LocalDateTime.of(2018,10,20,12,47,55)
        //When
        val transformedStringToDate = stringAsDate.toDate()
        //Then
        assertThat(transformedStringToDate, equalTo(expectedDate) )
    }

}