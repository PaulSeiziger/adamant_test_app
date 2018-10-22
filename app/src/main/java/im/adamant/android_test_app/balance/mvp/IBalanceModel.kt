package im.adamant.android_test_app.balance.mvp

import im.adamant.android_test_app.currencies.CurrencyTransferEntity
import im.adamant.android_test_app.entities.CurrencyCardItem
import im.adamant.android_test_app.interactors.AccountInteractor
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by dmitry on 04.05.18.
 */
interface IBalanceModel {
    fun getCards(): Flowable<List<CurrencyCardItem>>
    fun getCurrencyTransfers(currencyCardItem: CurrencyCardItem): Single<List<CurrencyTransferEntity>>
}

class BalanceModel @Inject constructor(private val accountInteractor: AccountInteractor) : IBalanceModel {

    override fun getCards(): Flowable<List<CurrencyCardItem>> {
        return accountInteractor.currencyItemCards
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCurrencyTransfers(currencyCardItem: CurrencyCardItem): Single<List<CurrencyTransferEntity>> {
        return accountInteractor.getLastTransfersByCurrencyAbbr(currencyCardItem.abbreviation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
