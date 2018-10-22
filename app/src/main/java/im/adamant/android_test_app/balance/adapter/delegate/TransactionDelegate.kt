package im.adamant.android_test_app.balance.adapter.delegate

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import com.google.gson.Gson
import im.adamant.android_test_app.R
import im.adamant.android_test_app.core.adapters.AbstractAdapterDelegate
import im.adamant.android_test_app.currencies.CurrencyTransferEntity
import im.adamant.android_test_app.entities.CurrencyCardItem
import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionDelegate : AbstractAdapterDelegate<Any, Any, TransactionDelegate.Holder>() {

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is CurrencyTransferEntity
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, item: Any, items: MutableList<Any>, position: Int) {
        item as CurrencyTransferEntity

        with(holder) {
            tvName.text = item.contactName
            tvNum.text = item.address

            tvPrice.text = "${item.amount.toPlainString()} ${item.currencyAbbreviation.toUpperCase()}"
            tvDate.text = SimpleDateFormat("dd MMM, hh:mm").format(Calendar.getInstance().time)
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView

        val tvName = itemView.tvName
        val tvNum = itemView.tvNum
        val tvPrice = itemView.tvPrice
        val tvDate = itemView.tvDate
    }
}

