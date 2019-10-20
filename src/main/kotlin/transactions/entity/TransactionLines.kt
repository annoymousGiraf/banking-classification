package transactions.entity

class TransactionLines(private val transactionLines: Collection<TransactionLine>) {

    operator fun get(i: Int): TransactionLine {
        return transactionLines.elementAt(i)
    }
}


