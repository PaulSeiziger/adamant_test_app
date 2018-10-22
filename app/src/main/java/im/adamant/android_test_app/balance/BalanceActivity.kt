package im.adamant.android_test_app.balance

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import dagger.android.AndroidInjection

import im.adamant.android_test_app.R
import im.adamant.android_test_app.balance.adapter.BalanceFragmentPagerAdapter
import im.adamant.android_test_app.balance.mvp.IBalancePresenter
import im.adamant.android_test_app.balance.mvp.IBalanceView
import im.adamant.android_test_app.core.adapters.DelegationAdapter
import im.adamant.android_test_app.currencies.CurrencyTransferEntity
import im.adamant.android_test_app.currencies.SupportedCurrencyType
import im.adamant.android_test_app.entities.CurrencyCardItem
import kotlinx.android.synthetic.main.activity_balance.*
import javax.inject.Inject

class BalanceActivity : AppCompatActivity(), IBalanceView {

    override var loadingDialog: View? = null
    override var showLayout: View? = null

    @Inject
    override lateinit var presenter: IBalancePresenter

    private lateinit var tabAdapter: BalanceFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        setSupportActionBar(toolbar)

        loadingDialog = progressBar
        showLayout = mainLayout

        presenter.attachView(this)

        tabAdapter = BalanceFragmentPagerAdapter(supportFragmentManager)

        viewpager.adapter = tabAdapter
        viewpager.offscreenPageLimit = tabAdapter.count

        tabLayout.setupWithViewPager(viewpager)

        presenter.getCards()
    }

    override fun cardsUploaded(items: List<CurrencyCardItem>) {

    }

    override fun cardsUploadedFailed() {
        Toast.makeText(this, "не удалось загрузить данные", Toast.LENGTH_SHORT).show()
    }

    override fun transfersUploaded(items: List<CurrencyTransferEntity>) {

    }

    override fun transfersUploadedFailed() {
        Toast.makeText(this, "не удалось загрузить данные", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logoutMenu) {
            Toast.makeText(this, "logout)", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getType(): SupportedCurrencyType? = null

    override fun onDestroy() {
        presenter.detachView(this)
        super.onDestroy()
    }
}
