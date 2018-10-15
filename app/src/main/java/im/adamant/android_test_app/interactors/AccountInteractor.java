package im.adamant.android_test_app.interactors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.currencies.CurrencyInfoDriver;
import im.adamant.android_test_app.currencies.CurrencyTransferEntity;
import im.adamant.android_test_app.currencies.SupportedCurrencyType;
import im.adamant.android_test_app.entities.CurrencyCardItem;
import im.adamant.android_test_app.helpers.BalanceConvertHelper;
import im.adamant.android_test_app.helpers.Settings;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AccountInteractor {
    private AdamantApiWrapper api;
    private Map<SupportedCurrencyType, CurrencyInfoDriver> infoDrivers;

    public AccountInteractor(AdamantApiWrapper api, Map<SupportedCurrencyType, CurrencyInfoDriver> infoDrivers) {
        this.api = api;
        this.infoDrivers = infoDrivers;
    }

    public Flowable<List<CurrencyCardItem>> getCurrencyItemCards() {
        return Flowable.fromCallable(() -> {
            List<CurrencyCardItem> list = new ArrayList<>();

            for (SupportedCurrencyType currencyType : SupportedCurrencyType.values()){
                if (infoDrivers.containsKey(currencyType)){
                    CurrencyInfoDriver driver = infoDrivers.get(currencyType);
                    if (driver == null){continue;}

                    CurrencyCardItem item = new CurrencyCardItem();
                    item.setAddress(driver.getAddress());
                    item.setBalance(driver.getBalance());
                    item.setTitleString(driver.getTitle());
                    item.setPrecision(driver.getPrecision());
                    item.setBackgroundLogoResource(driver.getBackgroundLogoResource());
                    item.setAbbreviation(currencyType.name());
                    list.add(item);
                }

            }

            return list;
        })
        .subscribeOn(Schedulers.computation());
    }

    public Single<List<CurrencyTransferEntity>> getLastTransfersByCurrencyAbbr(String abbreviation) {
        try {
            SupportedCurrencyType supportedCurrencyType = SupportedCurrencyType.valueOf(abbreviation);
            if (infoDrivers.containsKey(supportedCurrencyType)){
                CurrencyInfoDriver driver = infoDrivers.get(supportedCurrencyType);
                if (driver == null){return Single.error(new NullPointerException());}

                return driver.getLastTransfers();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return Single.error(new Exception("Not found currency info driver"));
    }

    public void logout() {
        api.logout();
    }
}
