package im.adamant.android_test_app.balance.mvp

import im.adamant.android_test_app.currencies.SupportedCurrencyType
import im.adamant.android_test_app.entities.CurrencyCardItem
import services.mobiledev.ru.cheap.mvp.IPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by dmitry on 04.05.18.
 */
interface IBalancePresenter : IPresenter<IBalanceView, IBalanceModel> {
    fun getCards()
    fun getCard(type: SupportedCurrencyType)
    fun getCurrencyTransfers(currencyCardItem: CurrencyCardItem, view: IBalanceView)
}

class BalancePresenter @Inject constructor(override var model: IBalanceModel) : IBalancePresenter {

    override val subscriptions: CompositeDisposable = CompositeDisposable()

    override var view: IBalanceView? = null

    var attachADMView: IBalanceView? = null
    var attachETHView: IBalanceView? = null
    var attachBNBView: IBalanceView? = null
    var attachLCDView: IBalanceView? = null

    var viewArray = arrayListOf<IBalanceView?>()

    var cardItems: List<CurrencyCardItem>? = null

    override fun getCards() {
        view?.showLoadingDialog()

        unsubscribeOnDestroy(model.getCards()
                .doAfterTerminate { view?.dismissLoadingDialog() }
                .subscribe({
                    cardItems = it

                    viewArray.forEach { view ->
                        view?.cardsUploaded(cardItems
                                ?.filter { it.abbreviation == view.getType()?.name }
                                ?: cardItems!!)
                    }

                }, {
                    view?.cardsUploadedFailed()
                    it.printStackTrace()
                })
        )
    }

    override fun getCurrencyTransfers(currencyCardItem: CurrencyCardItem, view: IBalanceView) {
        view.showLoadingDialog()

        unsubscribeOnDestroy(model.getCurrencyTransfers(currencyCardItem)
                .doAfterTerminate { view.dismissLoadingDialog() }
                .subscribe({
                    view.transfersUploaded(it)
                }, {
                    view.transfersUploadedFailed()
                    it.printStackTrace()
                })
        )
    }

    override fun getCard(type: SupportedCurrencyType) {
        if (cardItems != null) {
            viewArray.find {
                it?.getType() == type
            }?.cardsUploaded(cardItems
                    ?.filter { it.abbreviation == attachADMView?.getType()?.name }
                    ?: cardItems!!)
        }
    }

    override fun attachView(view: IBalanceView) {
        when {
            view.getType() == SupportedCurrencyType.ADM -> {
                attachADMView = view
                viewArray.add(view)
            }
            view.getType() == SupportedCurrencyType.ETH -> {
                attachETHView = view
                viewArray.add(view)
            }
            view.getType() == SupportedCurrencyType.BNB -> {
                attachBNBView = view
                viewArray.add(view)
            }
            view.getType() == SupportedCurrencyType.LCD -> {
                attachLCDView = view
                viewArray.add(view)
            }
            else -> {
                this.view = view
            }
        }
    }

    override fun detachView(view: IBalanceView) {
        this.view = null

        attachADMView = null
        attachETHView = null
        attachBNBView = null
        attachLCDView = null
    }

    override fun onDestroyView() {
        clearRequestQueue()
    }


}
