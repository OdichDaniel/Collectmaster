package org.odk.collect.app.location;

import com.jakewharton.rxrelay2.BehaviorRelay;

import org.odk.collect.app.architecture.rx.RxMVVMViewModel;
import org.odk.collect.app.injection.config.scopes.PerViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;

@PerViewModel
public class GeoViewModel extends RxMVVMViewModel {

    private final BehaviorRelay<Boolean> isReloadEnabled = BehaviorRelay.createDefault(false);
    private final BehaviorRelay<Boolean> isShowEnabled = BehaviorRelay.createDefault(false);

    @Inject
    GeoViewModel() {

    }

    Observable<Boolean> isReloadEnabled() {
        return isReloadEnabled.hide();
    }

    Observable<Boolean> isShowEnabled() {
        return isShowEnabled.hide();
    }
}
