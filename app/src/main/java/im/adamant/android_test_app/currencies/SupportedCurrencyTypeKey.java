package im.adamant.android_test_app.currencies;

import dagger.MapKey;

@MapKey
public @interface SupportedCurrencyTypeKey {
    SupportedCurrencyType value();
}
