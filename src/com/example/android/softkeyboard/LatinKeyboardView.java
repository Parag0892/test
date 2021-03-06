/*
 * Copyright (C) 2008-2009 The Android Open Source Project
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

package com.example.android.softkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;





/**
 * Date: 4/6/7 
 * Author: MAYANK NEVE 
 * Extending the keyborad view
 */

// veiw that renders the virtual keyboard . it handels the rendering of the keys and key presses and touch movements 
public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatinKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    // if keyboard.keycode_cancel then the if parts which is  sending some information in the softkeyboard 

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
        	
        	// first get the object of the onkeyboardlistener -- sends a key press to the listener -- matlab ye softkeyboard wale me chala jaega 
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }
    
    // input method subtype is used for storing the meta related with the IME . EX: language , voice-text , used in switch and settings
    // brings the subtype input method directly 

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();// get the current displayed keybooard
        keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        
        invalidateAllKeys(); // request to redraw the entire keyboard
    }
}
