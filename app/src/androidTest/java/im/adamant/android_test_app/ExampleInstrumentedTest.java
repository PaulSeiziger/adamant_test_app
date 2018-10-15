package im.adamant.android_test_app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import im.adamant.android_test_app.dagger.AppComponent;
import im.adamant.android_test_app.dagger.DaggerAppComponent;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private AppComponent component;

    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        component = DaggerAppComponent.builder().context(appContext).build();
    }

    @Test
    public void useAppContext() {

    }
}
