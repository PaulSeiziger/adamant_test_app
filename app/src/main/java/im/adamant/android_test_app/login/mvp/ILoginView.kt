package im.adamant.android_test_app.login.mvp

import services.mobiledev.ru.cheap.mvp.IView

/**
 * Created by dmitry on 04.05.18.
 */

interface ILoginView : IView<ILoginPresenter> {
    fun userAuthorized()
    fun userAuthorizedFailed()
}
