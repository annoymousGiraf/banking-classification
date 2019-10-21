package transactions.reader

import java.io.File

object InputReaderFactory {
    fun of(inputFile : File): TransactionInputReader =
        when (inputFile.extension) {
            "csv" -> CSVTransactionReader(inputFile.readText())
            else -> throw IllegalArgumentException("unsupported input type")
        }

}
