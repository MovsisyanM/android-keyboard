/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.futo.inputmethod.latin.utils;

import static org.futo.inputmethod.latin.utils.ImportantNoticeUtils.KEY_TIMESTAMP_OF_CONTACTS_NOTICE;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.runner.AndroidJUnit4;

import org.futo.inputmethod.latin.settings.SettingsValues;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ImportantNoticeUtilsTests {

    private ImportantNoticePreferences mImportantNoticePreferences;

    @Mock private SettingsValues mMockSettingsValues;

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    private static class ImportantNoticePreferences {
        private final SharedPreferences mPref;

        private Long mLastTime;

        public ImportantNoticePreferences(final Context context) {
            mPref = ImportantNoticeUtils.getImportantNoticePreferences(context);
        }

        private Integer getInt(final String key) {
            if (mPref.contains(key)) {
                return mPref.getInt(key, 0);
            }
            return null;
        }

        public Long getLong(final String key) {
            if (mPref.contains(key)) {
                return mPref.getLong(key, 0);
            }
            return null;
        }

        private void putInt(final String key, final Integer value) {
            if (value == null) {
                removePreference(key);
            } else {
                mPref.edit().putInt(key, value).apply();
            }
        }

        private void putLong(final String key, final Long value) {
            if (value == null) {
                removePreference(key);
            } else {
                mPref.edit().putLong(key, value).apply();
            }
        }

        private void removePreference(final String key) {
            mPref.edit().remove(key).apply();
        }

        public void save() {
            mLastTime = getLong(KEY_TIMESTAMP_OF_CONTACTS_NOTICE);
        }

        public void restore() {
            putLong(KEY_TIMESTAMP_OF_CONTACTS_NOTICE, mLastTime);
        }

        public void clear() {
            removePreference(KEY_TIMESTAMP_OF_CONTACTS_NOTICE);
        }
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mImportantNoticePreferences = new ImportantNoticePreferences(getContext());
        mImportantNoticePreferences.save();
        when(mMockSettingsValues.isPersonalizationEnabled()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
        mImportantNoticePreferences.restore();
    }

    @Test
    public void testPersonalizationSetting() {
        mImportantNoticePreferences.clear();

        // Personalization enabled.
        when(mMockSettingsValues.isPersonalizationEnabled()).thenReturn(true);
        assertEquals("Current boolean with personalization enabled", true,
                ImportantNoticeUtils.shouldShowImportantNotice(getContext(), mMockSettingsValues));

        // Personalization disabled.
        when(mMockSettingsValues.isPersonalizationEnabled()).thenReturn(false);
        assertEquals("Current boolean with personalization disabled", false,
                ImportantNoticeUtils.shouldShowImportantNotice(getContext(), mMockSettingsValues));
    }
}
