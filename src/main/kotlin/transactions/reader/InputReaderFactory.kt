package transactions.reader

import java.io.File

object InputReaderFactory {

    private const val CSV = "csv"

    fun of(inputFile : File): TransactionInputReader =
        when (inputFile.extension) {
            CSV -> CSVTransactionReader(inputFile.readText())
            else -> throw IllegalArgumentException("unsupported input type")
        }




}
