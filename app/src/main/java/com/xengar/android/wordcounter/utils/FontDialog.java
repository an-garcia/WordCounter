/*
 * Copyright (C) 2017 Angel Newton
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
package com.xengar.android.wordcounter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xengar.android.wordcounter.R;

/**
 * FontDialog in Preferences.
 */
public class FontDialog extends DialogPreference implements View.OnClickListener {

    private static final int MIN_FONT_SIZE = 6;
    private static final int MAX_FONT_SIZE = 100;
    private int fontSize = 0;
    private TextView text;


    public FontDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FontDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setPersistent(false);
        setDialogLayoutResource(R.layout.font_size_dialog);
    }

    @Override
    protected void onBindDialogView(View view) {
        text = (TextView) view.findViewById(R.id.font_size);
        fontSize  = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getContext()));
        text.setText(String.valueOf(fontSize));
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        // Set listener
        ImageView imageMinus = (ImageView) view.findViewById(R.id.minus);
        imageMinus.setOnClickListener(this);
        ImageView imagePlus = (ImageView) view.findViewById(R.id.plus);
        imagePlus.setOnClickListener(this);

        super.onBindDialogView(view);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.minus:
                if (fontSize > MIN_FONT_SIZE) {
                    fontSize--;
                    text.setText(String.valueOf(fontSize));
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                }
                break;

            case R.id.plus:
                if (fontSize < MAX_FONT_SIZE) {
                    fontSize++;
                    text.setText(String.valueOf(fontSize));
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                }
                break;
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        // save shared preferences
        String key = getContext().getString(R.string.pref_font_size);
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, String.valueOf(fontSize));
        editor.commit();
    }
}
