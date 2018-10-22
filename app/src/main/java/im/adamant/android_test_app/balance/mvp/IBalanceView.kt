package im.adamant.android_test_app.balance.mvp

import im.adamant.android_test_app.balance.mvp.IBalancePresenter
import im.adamant.android_test_app.currencies.CurrencyTransferEntity
import im.adamant.android_test_app.currencies.SupportedCurrencyType
import im.adamant.android_test_app.entities.CurrencyCardItem
import services.mobiledev.ru.cheap.mvp.IView

/**
 * Created by dmitry on 04.05.18.
 */

interface IBalanceView : IView<IBalancePresenter> {
    fun cardsUploaded(items: List<CurrencyCardItem>)
    fun cardsUploadedFailed()

    fun transfersUploaded(items: List<CurrencyTransferEntity>)
    fun transfersUploadedFailed()

    fun getType(): SupportedCurrencyType?
}
