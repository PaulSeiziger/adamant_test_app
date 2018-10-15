package im.adamant.android_test_app.currencies;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Single;

public interface CurrencyInfoDriver {
    BigDecimal getBalance();
    String getAddress();
    SupportedCurrencyType getCurrencyType();
    //TODO: Remove hardcoded values
    String getTitle();
    int getPrecision();
    int getBackgroundLogoResource();
    Single<List<CurrencyTransferEntity>> getLastTransfers();
}
