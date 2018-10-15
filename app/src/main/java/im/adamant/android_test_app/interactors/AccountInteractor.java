package im.adamant.android_test_app.interactors;

import java.math.BigDecimal;

import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.helpers.BalanceConvertHelper;
import im.adamant.android_test_app.helpers.Settings;
import io.reactivex.Flowable;

public class AccountInteractor {
    private AdamantApiWrapper api;

    public AccountInteractor(AdamantApiWrapper api) {
        this.api = api;
    }

    public String getAdamantAddress() {
        String address = "";
        if (api.isAuthorized()){
            address = api.getAccount().getAddress();
        }

        return address;
    }

    public Flowable<BigDecimal> getAdamantBalance() {
        return Flowable.fromCallable(() -> {
            if (api.isAuthorized()){
                return BalanceConvertHelper.convert(api.getAccount().getUnconfirmedBalance());
            } else {
                return BigDecimal.ZERO;
            }
        });
    }

    public void logout() {
        api.logout();
    }
}
