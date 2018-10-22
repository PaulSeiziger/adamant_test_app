package im.adamant.android_test_app.login

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import dagger.android.AndroidInjection

import im.adamant.android_test_app.R
import im.adamant.android_test_app.balance.BalanceActivity
import im.adamant.android_test_app.login.mvp.ILoginPresenter
import im.adamant.android_test_app.login.mvp.ILoginView
import im.adamant.android_test_app.login.mvp.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : Activity(), ILoginView {

    override var loadingDialog: View? = null
    override var showLayout: View? = null

    @Inject
    override lateinit var presenter: ILoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingDialog = progressBar
        //showLayout = mainLayout

        btnLogin.setOnClickListener {
            presenter.login("crisp verify apart brief same private jeans parent surge bamboo lawn satisfy")//etLoginKey.text.toString())
        }

        presenter.attachView(this)
    }

    override fun userAuthorized() {
        startActivity(Intent(this@LoginActivity, BalanceActivity::class.java))
    }

    override fun userAuthorizedFailed() {
        Toast.makeText(this, "не удалось авторизоваться", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.detachView(this)
        super.onDestroy()
    }
}
