package org.odk.collect.app.injection;

import android.app.Application;

import org.odk.collect.app.http.CollectServerClientTest;
import org.odk.collect.app.injection.config.AppComponent;
import org.odk.collect.app.injection.config.scopes.PerApplication;
import org.odk.collect.app.sms.SmsSenderJobTest;
import org.odk.collect.app.sms.SmsServiceTest;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@PerApplication
@Component(modules = {
        AndroidSupportInjectionModule.class,
        TestModule.class,
        ActivityBuilder.class
})
public interface TestComponent extends AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        TestComponent.Builder application(Application application);

        TestComponent build();
    }

    void inject(SmsSenderJobTest smsSenderJobTest);

    void inject(SmsServiceTest smsServiceTest);

    void inject(CollectServerClientTest collectServerClientTest);
}
