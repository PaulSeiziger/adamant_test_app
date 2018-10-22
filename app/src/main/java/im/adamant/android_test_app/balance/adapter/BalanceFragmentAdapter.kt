package im.adamant.android_test_app.balance.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import im.adamant.android_test_app.currencies.SupportedCurrencyType
import javax.inject.Inject

class BalanceFragmentPagerAdapter @Inject constructor(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val titles = SupportedCurrencyType.values()

    override fun getItem(position: Int): Fragment {
        return CurrencyTypeFragment.newInstance(titles[position])
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position].name
    }
}