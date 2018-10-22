package im.adamant.android_test_app.balance.adapter.delegate

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import im.adamant.android_test_app.R
import im.adamant.android_test_app.core.adapters.AbstractAdapterDelegate
import im.adamant.android_test_app.entities.CurrencyCardItem
import kotlinx.android.synthetic.main.item_card.view.*

class CardDelegate
    : AbstractAdapterDelegate<Any, Any, CardDelegate.Holder>() {

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is CurrencyCardItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, item: Any, items: MutableList<Any>, position: Int) {
        item as CurrencyCardItem

        with(holder) {
            tvBalanceContent.text = item.balance.toPlainString()

            tvNumberContent.text = item.address

            ivCopy.setOnClickListener {
                Toast.makeText(ivCopy.context, "copied", Toast.LENGTH_SHORT).show()
            }

            tvCreateQrCode.setOnClickListener {
                Toast.makeText(ivCopy.context, "qr-code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView

        val tvCardTitle = itemView.tvCardTitle
        val tvBalanceTitle = itemView.tvBalanceTitle
        val tvBalanceContent = itemView.tvBalanceContent
        val tvNumberTitle = itemView.tvNumberTitle
        val tvNumberContent = itemView.tvNumberContent
        val ivCopy = itemView.ivCopy
        val tvCreateQrCode = itemView.tvCreateQrCode
    }
}

