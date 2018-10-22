package im.adamant.android_test_app.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.goterl.lazycode.lazysodium.LazySodium;
import com.goterl.lazycode.lazysodium.LazySodiumAndroid;
import com.goterl.lazycode.lazysodium.SodiumAndroid;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import dagger.multibindings.IntoMap;
import im.adamant.android_test_app.MainActivity;
import im.adamant.android_test_app.balance.BalanceActivity;
import im.adamant.android_test_app.balance.adapter.CurrencyTypeFragment;
import im.adamant.android_test_app.balance.mvp.BalanceModel;
import im.adamant.android_test_app.balance.mvp.BalancePresenter;
import im.adamant.android_test_app.balance.mvp.IBalancePresenter;
import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.core.encryption.AdamantKeyGenerator;
import im.adamant.android_test_app.core.encryption.Encryptor;
import im.adamant.android_test_app.currencies.AdamantCurrencyInfoDriver;
import im.adamant.android_test_app.currencies.BinanceCoinInfoDriver;
import im.adamant.android_test_app.currencies.CurrencyInfoDriver;
import im.adamant.android_test_app.currencies.EthereumCurrencyInfoDriver;
import im.adamant.android_test_app.currencies.SupportedCurrencyType;
import im.adamant.android_test_app.currencies.SupportedCurrencyTypeKey;
import im.adamant.android_test_app.helpers.NaivePublicKeyStorageImpl;
import im.adamant.android_test_app.helpers.PublicKeyStorage;
import im.adamant.android_test_app.helpers.Settings;
import im.adamant.android_test_app.interactors.AccountInteractor;
import im.adamant.android_test_app.interactors.AuthorizeInteractor;
import im.adamant.android_test_app.login.LoginActivity;
import im.adamant.android_test_app.login.mvp.ILoginPresenter;
import im.adamant.android_test_app.login.mvp.LoginModel;
import im.adamant.android_test_app.login.mvp.LoginPresenter;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.wordlists.English;

@Module(includes = {AndroidSupportInjectionModule.class})
public abstract class AppModule {

    @Singleton
    @Provides
    public static Gson provideGson() {
        return new Gson();
    }


    @Singleton
    @Provides
    public static Settings provideSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        return new Settings(preferences);
    }

    @Singleton
    @Provides
    public static SeedCalculator provideSeedCalculator() {
        return new SeedCalculator();
    }

    @Singleton
    @Provides
    public static MnemonicGenerator provideMnemonic() {
        return new MnemonicGenerator(English.INSTANCE);
    }

    @Singleton
    @Provides
    public static LazySodium provideLazySodium() {
        SodiumAndroid sodium = new SodiumAndroid();
        return new LazySodiumAndroid(sodium);
    }

    @Singleton
    @Provides
    public static AdamantKeyGenerator providesKeyGenerator(SeedCalculator seedCalculator, MnemonicGenerator mnemonicGenerator, LazySodium sodium) {
        return new AdamantKeyGenerator(seedCalculator, mnemonicGenerator, sodium);
    }

    @Singleton
    @Provides
    public static Encryptor providesMessageEncryptor(LazySodium sodium) {
        return new Encryptor(sodium);
    }


    @Singleton
    @Provides
    public static AdamantApiWrapper provideAdamantApiWrapper(Settings settings, AdamantKeyGenerator keyGenerator) {
        return new AdamantApiWrapper(settings.getNodes(), keyGenerator);
    }

    @Singleton
    @Provides
    public static PublicKeyStorage providePublicKeyStorage(AdamantApiWrapper api) {
        return new NaivePublicKeyStorageImpl(api);
    }


    @Singleton
    @Provides
    public static AuthorizeInteractor provideAuthorizationInteractor(
            AdamantApiWrapper api,
            AdamantKeyGenerator keyGenerator
    ) {
        return new AuthorizeInteractor(api, keyGenerator);
    }

    @Singleton
    @Provides
    public static AccountInteractor provideAccountInteractor(
            AdamantApiWrapper api,
            Map<SupportedCurrencyType, CurrencyInfoDriver> infoDrivers
    ) {
        return new AccountInteractor(api, infoDrivers);
    }

    @IntoMap
    @SupportedCurrencyTypeKey(SupportedCurrencyType.ADM)
    @Singleton
    @Provides
    public static CurrencyInfoDriver provideAdamantInfoDriver(
            AdamantApiWrapper api
    ) {
        return new AdamantCurrencyInfoDriver(api);
    }

    @IntoMap
    @SupportedCurrencyTypeKey(SupportedCurrencyType.ETH)
    @Singleton
    @Provides
    public static CurrencyInfoDriver provideEthereumInfoDriver() {
        return new EthereumCurrencyInfoDriver();
    }

    @IntoMap
    @SupportedCurrencyTypeKey(SupportedCurrencyType.BNB)
    @Singleton
    @Provides
    public static CurrencyInfoDriver provideBinanceInfoDriver() {
        return new BinanceCoinInfoDriver();
    }


    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    public abstract MainActivity createMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    public abstract LoginActivity createLoginActivityInjector();

    @Singleton
    @Provides
    public static ILoginPresenter provideILoginPresenter(AdamantApiWrapper api,
                                                         AdamantKeyGenerator keyGenerator) {
        return new LoginPresenter(new LoginModel(new AuthorizeInteractor(api, keyGenerator)));
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = {BalanceActivityModule.class})
    public abstract BalanceActivity createBalanceActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = {CurrencyTypeFragmentModule.class})
    public abstract CurrencyTypeFragment createCurrencyTypeFragmentInjector();

    @Singleton
    @Provides
    public static IBalancePresenter provideIBalancePresenter(AdamantApiWrapper api,
                                                             Map<SupportedCurrencyType, CurrencyInfoDriver> infoDrivers) {
        return new BalancePresenter(new BalanceModel(new AccountInteractor(api, infoDrivers)));
    }
}
