package org.odk.collect.app.injection.config;

import android.app.Application;

import org.odk.collect.app.activities.FormDownloadList;
import org.odk.collect.app.activities.FormEntryActivity;
import org.odk.collect.app.activities.InstanceUploaderList;
import org.odk.collect.app.adapters.InstanceUploaderAdapter;
import org.odk.collect.app.application.Collect;
import org.odk.collect.app.fragments.DataManagerList;
import org.odk.collect.app.http.CollectServerClient;
import org.odk.collect.app.injection.ActivityBuilder;
import org.odk.collect.app.injection.config.scopes.PerApplication;
import org.odk.collect.app.logic.PropertyManager;
import org.odk.collect.app.preferences.ServerPreferencesFragment;
import org.odk.collect.app.tasks.InstanceServerUploaderTask;
import org.odk.collect.app.tasks.sms.SmsSentBroadcastReceiver;
import org.odk.collect.app.tasks.sms.SmsNotificationReceiver;
import org.odk.collect.app.tasks.sms.SmsSender;
import org.odk.collect.app.tasks.sms.SmsService;
import org.odk.collect.app.utilities.AuthDialogUtility;
import org.odk.collect.app.utilities.DownloadFormListUtils;
import org.odk.collect.app.utilities.FormDownloader;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Primary module, bootstraps the injection system and
 * injects the main Collect instance here.
 * <p>
 * Shouldn't be modified unless absolutely necessary.
 */
@PerApplication
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(Collect collect);

    void inject(SmsService smsService);

    void inject(SmsSender smsSender);

    void inject(SmsSentBroadcastReceiver smsSentBroadcastReceiver);

    void inject(SmsNotificationReceiver smsNotificationReceiver);

    void inject(InstanceUploaderList instanceUploaderList);

    void inject(InstanceUploaderAdapter instanceUploaderAdapter);

    void inject(DataManagerList dataManagerList);

    void inject(PropertyManager propertyManager);

    void inject(FormEntryActivity formEntryActivity);

    void inject(InstanceServerUploaderTask uploader);

    void inject(CollectServerClient collectClient);

    void inject(ServerPreferencesFragment serverPreferencesFragment);

    void inject(FormDownloader formDownloader);

    void inject(DownloadFormListUtils downloadFormListUtils);

    void inject(AuthDialogUtility authDialogUtility);
  
    void inject(FormDownloadList formDownloadList);
}
