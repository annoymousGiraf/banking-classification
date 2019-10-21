package transactions.reader

import transactions.entity.TransactionLines

interface TransactionInputReader {

    fun read(): TransactionLines


}
