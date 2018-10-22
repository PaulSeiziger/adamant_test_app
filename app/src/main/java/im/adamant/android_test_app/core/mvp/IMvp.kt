package services.mobiledev.ru.cheap.mvp

import android.view.View
import im.adamant.android_test_app.core.extentions.animateHide
import im.adamant.android_test_app.core.extentions.animateShow
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by bloold on 01.04.18.
 */
interface IView<Presenter> {
    var presenter: Presenter

    var loadingDialog: View?
    var showLayout: View?

    fun showLoadingDialog(message: String? = null) {
        showLayout?.animateHide(getAnimateDuration())
        loadingDialog?.animateShow(getAnimateDuration())
    }

    fun getAnimateDuration(): Long = 300L

    fun dismissLoadingDialog() {
        loadingDialog?.animateHide(getAnimateDuration())
        showLayout?.animateShow(getAnimateDuration())
    }
}

interface IPresenter<View, Model> {
    var view: View?
    var model: Model

    val subscriptions: CompositeDisposable

    fun attachView(view: View)
    fun detachView(view: View)

    fun onDestroyView() {
        clearRequestQueue()
    }

    fun unsubscribeOnDestroy(disposable: Disposable) {
        subscriptions.add(disposable)
    }

    fun clearRequestQueue() {
        subscriptions.clear()
    }
}

interface IModel {

}