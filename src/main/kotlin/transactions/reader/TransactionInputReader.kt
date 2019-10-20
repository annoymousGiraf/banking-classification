package transactions.reader

import transactions.entity.TransactionLines

interface TransactionInputReader {
    fun read(transactionLines: Array<String>): TransactionLines


}
