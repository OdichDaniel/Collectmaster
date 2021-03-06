package org.odk.collect.app.injection.config;

import android.app.Application;
import android.content.Context;
import android.telephony.SmsManager;

import org.odk.collect.app.dao.FormsDao;
import org.odk.collect.app.dao.InstancesDao;
import org.odk.collect.app.events.RxEventBus;
import org.odk.collect.app.http.CollectServerClient;
import org.odk.collect.app.http.HttpClientConnection;
import org.odk.collect.app.http.OpenRosaHttpInterface;
import org.odk.collect.app.injection.ViewModelBuilder;
import org.odk.collect.app.injection.config.architecture.ViewModelFactoryModule;
import org.odk.collect.app.injection.config.scopes.PerApplication;
import org.odk.collect.app.tasks.sms.SmsSubmissionManager;
import org.odk.collect.app.tasks.sms.contracts.SmsSubmissionManagerContract;
import org.odk.collect.app.utilities.WebCredentialsUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Add Application level providers here, i.e. if you want to
 * inject something into the Collect instance.
 */
@Module(includes = {ViewModelFactoryModule.class, ViewModelBuilder.class})
public class AppModule {

    @Provides
    SmsManager provideSmsManager() {
        return SmsManager.getDefault();
    }

    @Provides
    SmsSubmissionManagerContract provideSmsSubmissionManager(Application application) {
        return new SmsSubmissionManager(application);
    }

    @Provides
    Context context(Application application) {
        return application;
    }

    @Provides
    InstancesDao provideInstancesDao() {
        return new InstancesDao();
    }

    @Provides
    FormsDao provideFormsDao() {
        return new FormsDao();
    }

    @PerApplication
    @Provides
    RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    public OpenRosaHttpInterface provideHttpInterface() {
        return new HttpClientConnection();
    }

    @Provides
    public CollectServerClient provideCollectServerClient(OpenRosaHttpInterface httpInterface, WebCredentialsUtils webCredentialsUtils) {
        return new CollectServerClient(httpInterface, webCredentialsUtils);
    }

    @Provides
    public WebCredentialsUtils provideWebCredentials() {
        return new WebCredentialsUtils();
    }

}
