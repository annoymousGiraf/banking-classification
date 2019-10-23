package extention

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") 

fun String.toDate() : LocalDateTime{
    return LocalDateTime.parse(this, formatter)
}