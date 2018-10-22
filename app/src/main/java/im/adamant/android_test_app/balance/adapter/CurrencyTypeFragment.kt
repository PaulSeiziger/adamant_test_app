package im.adamant.android_test_app.balance.adapter


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import im.adamant.android_test_app.R
import im.adamant.android_test_app.balance.adapter.delegate.CardDelegate
import im.adamant.android_test_app.balance.adapter.delegate.TransactionDelegate
import im.adamant.android_test_app.balance.mvp.IBalancePresenter
import im.adamant.android_test_app.balance.mvp.IBalanceView
import im.adamant.android_test_app.core.adapters.DelegationAdapter
import im.adamant.android_test_app.currencies.CurrencyTransferEntity
import im.adamant.android_test_app.currencies.SupportedCurrencyType
import im.adamant.android_test_app.entities.CurrencyCardItem
import kotlinx.android.synthetic.main.fragment_currency_type.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class CurrencyTypeFragment : Fragment(), IBalanceView {

    companion object {
        private const val EXTRA_CURRENCY_TYPE = "currency_type"

        fun newInstance(currencyType: SupportedCurrencyType): CurrencyTypeFragment {
            return CurrencyTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_CURRENCY_TYPE, currencyType.name)
                }
            }
        }
    }

    @Inject
    override lateinit var presenter: IBalancePresenter

    override var loadingDialog: View? = null
    override var showLayout: View? = null

    private var currencyType: SupportedCurrencyType? = null

    private val cardsAdapter = DelegationAdapter<Any>()
    private val transactionsAdapter = DelegationAdapter<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)

        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(EXTRA_CURRENCY_TYPE) == true) {
            currencyType = SupportedCurrencyType.valueOf(
                    arguments?.getString(EXTRA_CURRENCY_TYPE) ?: SupportedCurrencyType.ADM.name)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_currency_type, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        rvCards.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        rvCards.adapter = cardsAdapter
        //rvCards.addItemDecoration

        cardsAdapter.manager.addDelegate(CardDelegate())

        rvTransactions.layoutManager = LinearLayoutManager(context)
        rvTransactions.adapter = transactionsAdapter

        transactionsAdapter.manager.addDelegate(TransactionDelegate())

        if (currencyType != null) {
            presenter.getCard(currencyType!!)
        }
    }

    override fun getType(): SupportedCurrencyType? = currencyType

    override fun cardsUploaded(items: List<CurrencyCardItem>) {
        cardsAdapter.addAll(items)
        if (items.isNotEmpty()) {
            presenter.getCurrencyTransfers(items.first(), this)
        }
    }

    override fun cardsUploadedFailed() {

    }

    override fun transfersUploaded(items: List<CurrencyTransferEntity>) {
        transactionsAdapter.addAll(items)
    }

    override fun transfersUploadedFailed() {

    }

    override fun onDestroyView() {
        presenter.detachView(this)
        super.onDestroyView()
    }

}
