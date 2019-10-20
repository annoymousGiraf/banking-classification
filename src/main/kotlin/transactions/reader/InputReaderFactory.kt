package transactions.reader

object InputReaderFactory {
    fun of(inputType: String): TransactionInputReader =
        when (inputType) {
            "csv" -> CSVTransactionReader()
            else -> throw IllegalArgumentException("unsupported input type")
        }

}
