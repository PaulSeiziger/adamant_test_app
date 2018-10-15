package im.adamant.android_test_app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import im.adamant.android_test_app.core.exceptions.NotAuthorizedException;
import im.adamant.android_test_app.helpers.BalanceConvertHelper;
import im.adamant.android_test_app.helpers.LoggerHelper;
import im.adamant.android_test_app.interactors.AccountInteractor;
import im.adamant.android_test_app.interactors.AuthorizeInteractor;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity {

    @Inject
    AuthorizeInteractor authorizeInteractor;

    @Inject
    AccountInteractor accountInteractor;

    TextView balanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceView = findViewById(R.id.balance);

        Disposable disposable = authorizeInteractor
                .authorize("crisp verify apart brief same private jeans parent surge bamboo lawn satisfy")
                .flatMap(authorization -> {
                    if (authorization.isSuccess()) {
                        return accountInteractor.getAdamantBalance();
                    } else {
                        return Flowable.error(
                                new NotAuthorizedException(
                                        authorization.getError()
                                )
                        );
                    }
                })
                .subscribe(
                        balance -> {
                            String balanceText = balance + " " + getString(R.string.adm_abbr);
                            balanceView.setText(balanceText);
                        },
                        error -> {
                            LoggerHelper.e("ERR", error.getMessage(), error);
                        }
                );

    }
}
