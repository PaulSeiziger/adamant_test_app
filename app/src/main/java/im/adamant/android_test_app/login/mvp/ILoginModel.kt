package im.adamant.android_test_app.login.mvp

import im.adamant.android_test_app.core.exceptions.NotAuthorizedException
import im.adamant.android_test_app.core.responses.Authorization
import im.adamant.android_test_app.helpers.LoggerHelper
import im.adamant.android_test_app.interactors.AuthorizeInteractor
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import services.mobiledev.ru.cheap.mvp.IModel
import javax.inject.Inject

/**
 * Created by dmitry on 04.05.18.
 */
interface ILoginModel{
    fun login(key: String): Flowable<Authorization>
}

class LoginModel @Inject constructor(private val authorizeInteractor: AuthorizeInteractor) : ILoginModel {

    override fun login(key: String): Flowable<Authorization>{
        return authorizeInteractor
                .authorize(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
