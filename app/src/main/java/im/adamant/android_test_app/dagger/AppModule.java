package im.adamant.android_test_app.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.goterl.lazycode.lazysodium.LazySodium;
import com.goterl.lazycode.lazysodium.LazySodiumAndroid;
import com.goterl.lazycode.lazysodium.SodiumAndroid;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import im.adamant.android_test_app.MainActivity;
import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.core.encryption.AdamantKeyGenerator;
import im.adamant.android_test_app.core.encryption.Encryptor;
import im.adamant.android_test_app.helpers.NaivePublicKeyStorageImpl;
import im.adamant.android_test_app.helpers.PublicKeyStorage;
import im.adamant.android_test_app.helpers.Settings;
import im.adamant.android_test_app.interactors.AccountInteractor;
import im.adamant.android_test_app.interactors.AuthorizeInteractor;
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
    public static MnemonicGenerator provideMnemonic(){
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
            AdamantApiWrapper api
    ) {
        return new AccountInteractor(api);
    }


    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    public abstract MainActivity createMainActivityInjector();
}
