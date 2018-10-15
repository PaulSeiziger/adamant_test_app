package im.adamant.android_test_app.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import im.adamant.android_test_app.AdamantApplication;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        AppComponent build();
    }

    void inject(AdamantApplication app);
}
