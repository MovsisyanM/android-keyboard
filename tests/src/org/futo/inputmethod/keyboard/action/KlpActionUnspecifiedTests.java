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

package org.futo.inputmethod.keyboard.action;

import android.test.suitebuilder.annotation.LargeTest;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodSubtype;

import org.futo.inputmethod.keyboard.internal.KeyboardIconsSet;
import org.futo.inputmethod.latin.utils.SubtypeLocaleUtils;

@LargeTest
public class KlpActionUnspecifiedTests extends KlpActionTestsBase {
    public void testActionUnspecified() {
        final ExpectedActionKey expectedKey = ExpectedActionKey.newIconKey(
                KeyboardIconsSet.NAME_ENTER_KEY);
        for (final InputMethodSubtype subtype : mSubtypesWhoseNameIsDisplayedInItsLocale) {
            final String tag = "unspecifiled "
                    + SubtypeLocaleUtils.getSubtypeNameForLogging(subtype);
            doTestActionKey(tag, subtype, EditorInfo.IME_ACTION_UNSPECIFIED, expectedKey);
        }
    }
}
