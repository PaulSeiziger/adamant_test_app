package im.adamant.android_test_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import im.adamant.android_test_app.core.exceptions.NotAuthorizedException;
import im.adamant.android_test_app.helpers.BalanceConvertHelper;
import im.adamant.android_test_app.helpers.LoggerHelper;
import im.adamant.android_test_app.interactors.AccountInteractor;
import im.adamant.android_test_app.interactors.AuthorizeInteractor;
import im.adamant.android_test_app.login.LoginActivity;
import io.reactivex.Flowable;
import io.reactivex.Single;
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

        //нет отписки, нет обработки ошибки например отстутствия интернета, приложение должно упасть в таком случае
        Disposable disposable = authorizeInteractor
                .authorize("crisp verify apart brief same private jeans parent surge bamboo lawn satisfy")
                .flatMap(authorization -> {
                    if (authorization.isSuccess()) {
                        return accountInteractor.getCurrencyItemCards();
                    } else {
                        return Flowable.error(
                                new NotAuthorizedException(
                                        authorization.getError()
                                )
                        );
                    }
                })
                .flatMapIterable(currencyCardItems -> currencyCardItems)
                .doOnNext(currencyCardItem -> {
                    LoggerHelper.d("CurrencyCard", currencyCardItem.getAbbreviation());
                    LoggerHelper.d("CurrencyCard", currencyCardItem.getBalance().toString());
                })
                .flatMap(currencyCardItem ->
                        accountInteractor.getLastTransfersByCurrencyAbbr(
                                currencyCardItem.getAbbreviation()
                        ).toFlowable()
                )
                .flatMapIterable(transfers -> transfers)
                .doOnNext(transfer -> {
                    LoggerHelper.d("Transfer", transfer.getCurrencyAbbreviation());
                    LoggerHelper.d("Transfer", transfer.getAmount().toString());
                })

                .subscribe();

        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}
