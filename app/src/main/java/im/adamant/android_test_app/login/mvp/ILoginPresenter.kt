package im.adamant.android_test_app.login.mvp

import dagger.android.AndroidInjection
import services.mobiledev.ru.cheap.mvp.IPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by dmitry on 04.05.18.
 */
interface ILoginPresenter : IPresenter<ILoginView, ILoginModel> {
    fun login(key: String)
}

class LoginPresenter @Inject constructor(override var model: ILoginModel) : ILoginPresenter {

    override val subscriptions: CompositeDisposable = CompositeDisposable()

    override var view: ILoginView? = null

    override fun login(key: String) {
        view?.showLoadingDialog()

        unsubscribeOnDestroy(model.login(key)
                .doAfterTerminate { view?.dismissLoadingDialog() }
                .subscribe({
                    view?.userAuthorized()
                }, {
                    view?.userAuthorizedFailed()
                    it.printStackTrace()
                }))
    }

    override fun attachView(view: ILoginView) {
        this.view = view
    }

    override fun detachView(view: ILoginView) {
        if (this.view?.equals(view) == true) {
            this.view = null
        }
    }

    override fun onDestroyView() {
        clearRequestQueue()
    }


}
