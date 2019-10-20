package transactions.reader

import transactions.entity.TransactionLine
import transactions.entity.TransactionLines

class CSVTransactionReader : TransactionInputReader {
    override fun read(transactionLines: Array<String>): TransactionLines {
        return TransactionLines(transactionLines.map { TransactionLine(it.split(",")[0]) })
    }

}
