/*
 * Copyright (C) 2017 Angel Garcia
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
package com.xengar.android.wordcounter.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.xengar.android.wordcounter.R;
import com.xengar.android.wordcounter.data.Count;
import com.xengar.android.wordcounter.utils.ActivityUtils;

import static com.xengar.android.wordcounter.utils.Constants.CURRENT_TEXT;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

   private Count count = new Count();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        String currentText = getSharedText();
        if (currentText == null){
            currentText = ActivityUtils.getCurrentText(getApplicationContext());
        }
        if (currentText != null){
            textView.setText(currentText);
            ActivityUtils.saveStringToPreferences(getApplicationContext(), CURRENT_TEXT, null);
        }
    }

    /**
     * Get text from Shared intent.
     * @return String
     */
    private String getSharedText(){
        String text = null;
        ShareCompat.IntentReader intentReader = ShareCompat.IntentReader.from(this);
        if (intentReader.isShareIntent()) {
            text = intentReader.getText().toString();
        }
        return text;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_count:
                ActivityUtils.saveStringToPreferences(getApplicationContext(), CURRENT_TEXT, getCurrentText());
                ActivityUtils.launchCountActivity(getApplicationContext(), getCurrentText());
                return true;

            case R.id.action_quick_count:
                quickCount();
                return true;

            case R.id.action_share:
                ActivityUtils.launchShareText(this, getCurrentText());
                return true;

            case R.id.action_settings:
                ActivityUtils.saveStringToPreferences(getApplicationContext(), CURRENT_TEXT, getCurrentText());
                ActivityUtils.launchSettingsActivity(getApplicationContext());
                return true;

            case R.id.action_help:
                ActivityUtils.launchHelpActivity(getApplicationContext());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets current text
     * @return String
     */
    private String getCurrentText(){
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        return textView.getText().toString();
    }

    /**
     * Make a quick count of the words.
     */
    private void quickCount() {
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        String text = textView.getText().toString();
        ActivityUtils.calculateWords(getApplicationContext(), text, count);

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View layout = layoutInflater.inflate(R.layout.quick_count, null);
        ((TextView) layout.findViewById(R.id.words)).setText(String.valueOf(count.getWords()));
        ((TextView) layout.findViewById(R.id.characters)).setText(String.valueOf(count.getCharacters()));
        ((TextView) layout.findViewById(R.id.spaces)).setText(String.valueOf(count.getSpaces()));

        // Change font sizes
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        ((TextView) layout.findViewById(R.id.words_title)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView) layout.findViewById(R.id.characters_title)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView) layout.findViewById(R.id.spaces_title)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView) layout.findViewById(R.id.words)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView) layout.findViewById(R.id.characters)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView) layout.findViewById(R.id.spaces)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        AlertDialog alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle(R.string.quick_count)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setView(layout)
                //.setMessage(message)
                .create();
        alert.show();
    }


}
