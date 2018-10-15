package im.adamant.android_test_app.currencies;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import im.adamant.android_test_app.R;
import io.reactivex.Single;

public class EthereumCurrencyInfoDriver implements CurrencyInfoDriver {
    @Override
    public BigDecimal getBalance() {
        return BigDecimal.ZERO;
    }

    @Override
    public String getAddress() {
        return "Coming soon";
    }

    @Override
    public SupportedCurrencyType getCurrencyType() {
        return SupportedCurrencyType.ETH;
    }

    @Override
    public String getTitle() {
        return "ETHEREUM WALLET";
    }

    @Override
    public int getPrecision() {
        return 8;
    }

    @Override
    public int getBackgroundLogoResource() {
        return R.drawable.ic_ethereum_line;
    }

    @Override
    public Single<List<CurrencyTransferEntity>> getLastTransfers() {
        return Single.just(new ArrayList<>());
    }
}
