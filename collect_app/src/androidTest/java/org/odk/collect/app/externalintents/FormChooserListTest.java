package org.odk.collect.app.externalintents;

import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.odk.collect.app.activities.FormChooserList;

import java.io.IOException;

import static org.odk.collect.app.externalintents.ExportedActivitiesUtils.testDirectories;

@Suppress
// Frequent failures: https://github.com/opendatakit/collect/issues/796
public class FormChooserListTest {

    @Rule
    public ActivityTestRule<FormChooserList> formChooserListRule =
            new ExportedActivityTestRule<>(FormChooserList.class);

    @Test
    public void formChooserListMakesDirsTest() throws IOException {
        testDirectories();
    }

}
