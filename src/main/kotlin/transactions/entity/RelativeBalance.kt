package transactions.entity

import java.math.BigDecimal


data class RelativeBalance(val numberOfTransactions : Int , val balance: BigDecimal) {
    override fun toString(): String {
        return """
        Relative balance for the period is: $balance$
        Number of transactions included is: $numberOfTransactions"""
    }
}
