package im.adamant.android_test_app.interactors;

import im.adamant.android_test_app.core.AdamantApiWrapper;
import im.adamant.android_test_app.core.encryption.AdamantKeyGenerator;
import im.adamant.android_test_app.core.responses.Authorization;
import im.adamant.android_test_app.helpers.LoggerHelper;
import io.github.novacrypto.bip39.MnemonicValidator;
import io.github.novacrypto.bip39.Validation.InvalidChecksumException;
import io.github.novacrypto.bip39.Validation.InvalidWordCountException;
import io.github.novacrypto.bip39.Validation.UnexpectedWhiteSpaceException;
import io.github.novacrypto.bip39.Validation.WordNotFoundException;
import io.github.novacrypto.bip39.wordlists.English;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class AuthorizeInteractor {

    private AdamantApiWrapper api;
    private AdamantKeyGenerator keyGenerator;


    public AuthorizeInteractor(
            AdamantApiWrapper api,
            AdamantKeyGenerator keyGenerator
    ) {
        this.api = api;
        this.keyGenerator = keyGenerator;
    }

    public Flowable<Authorization> authorize(String passPhrase) {
        try {
            return api.authorize(passPhrase)
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(error -> {
                        LoggerHelper.e("authorization", error.getMessage(), error);
                        return Flowable.just(new Authorization());
                    });
        }catch (Exception ex){
            ex.printStackTrace();
            return Flowable.error(ex);
        }
    }

    public CharSequence generatePassPhrase() {
        return keyGenerator.generateNewPassphrase();
    }

    public boolean isValidPassphrase(String passphrase) {
        try {
            MnemonicValidator
                    .ofWordList(English.INSTANCE)
                    .validate(passphrase);
        } catch (InvalidChecksumException | InvalidWordCountException | WordNotFoundException | UnexpectedWhiteSpaceException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Flowable<Authorization> createNewAccount(String passPhrase) {
        return api.createNewAccount(passPhrase)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean isAuthorized() {
        return api.isAuthorized();
    }
}
