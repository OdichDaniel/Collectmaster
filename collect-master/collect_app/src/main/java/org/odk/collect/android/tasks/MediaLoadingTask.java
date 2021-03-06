package org.odk.collect.app.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import org.odk.collect.app.R;
import org.odk.collect.app.activities.FormEntryActivity;
import org.odk.collect.app.application.Collect;
import org.odk.collect.app.dao.helpers.ContentResolverHelper;
import org.odk.collect.app.exception.GDriveConnectionException;
import org.odk.collect.app.fragments.dialogs.ProgressDialogFragment;
import org.odk.collect.app.logic.FormController;
import org.odk.collect.app.utilities.FileUtils;
import org.odk.collect.app.utilities.ImageConverter;
import org.odk.collect.app.utilities.MediaUtils;
import org.odk.collect.app.utilities.ToastUtils;
import org.odk.collect.app.views.ODKView;
import org.odk.collect.app.widgets.BaseImageWidget;
import org.odk.collect.app.widgets.ImageWebViewWidget;
import org.odk.collect.app.widgets.QuestionWidget;

import java.io.File;
import java.lang.ref.WeakReference;

import timber.log.Timber;

public class MediaLoadingTask extends AsyncTask<Uri, Void, File> {

    private WeakReference<FormEntryActivity> formEntryActivity;

    public MediaLoadingTask(FormEntryActivity formEntryActivity) {
        onAttach(formEntryActivity);
    }

    public void onAttach(FormEntryActivity formEntryActivity) {
        this.formEntryActivity = new WeakReference<>(formEntryActivity);
    }

    public void onDetach() {
        formEntryActivity = null;
    }

    @Override
    protected File doInBackground(Uri... uris) {

        File instanceFile;
        FormController formController = Collect.getInstance().getFormController();

        if (formController != null) {
            instanceFile = formController.getInstanceFile();
            if (instanceFile != null) {
                String instanceFolder = instanceFile.getParent();
                String extension = ContentResolverHelper.getFileExtensionFromUri(uris[0]);
                String destMediaPath = instanceFolder + File.separator + System.currentTimeMillis() + extension;

                try {
                    File chosenFile = MediaUtils.getFileFromUri(formEntryActivity.get(), uris[0], MediaStore.Images.Media.DATA);
                    if (chosenFile != null) {
                        final File newFile = new File(destMediaPath);
                        FileUtils.copyFile(chosenFile, newFile);
                        QuestionWidget questionWidget = formEntryActivity.get().getWidgetWaitingForBinaryData();

                        // apply image conversion if the widget is an image widget
                        if (questionWidget instanceof BaseImageWidget ||
                                questionWidget instanceof ImageWebViewWidget) {
                            ImageConverter.execute(newFile.getPath(), questionWidget, formEntryActivity.get());
                        }

                        return newFile;
                    } else {
                        Timber.e("Could not receive chosen file");
                        formEntryActivity.get().runOnUiThread(() -> ToastUtils.showShortToastInMiddle(R.string.error_occured));
                        return null;
                    }
                } catch (GDriveConnectionException e) {
                    Timber.e("Could not receive chosen file due to connection problem");
                    formEntryActivity.get().runOnUiThread(() -> ToastUtils.showLongToastInMiddle(R.string.gdrive_connection_exception));
                    return null;
                }
            }
        }
        return null;

    }

    @Override
    protected void onPostExecute(File result) {
        Fragment prev = formEntryActivity.get().getSupportFragmentManager().findFragmentByTag(ProgressDialogFragment.COLLECT_PROGRESS_DIALOG_TAG);
        if (prev != null && !formEntryActivity.get().isInstanceStateSaved()) {
            ((DialogFragment) prev).dismiss();
        }

        ODKView odkView = formEntryActivity.get().getCurrentViewIfODKView();
        if (odkView != null) {
            odkView.setBinaryData(result);
        }

        formEntryActivity.get().saveAnswersForCurrentScreen(FormEntryActivity.DO_NOT_EVALUATE_CONSTRAINTS);
        formEntryActivity.get().refreshCurrentView();
    }
}
