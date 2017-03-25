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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MultiAutoCompleteTextView;

import com.xengar.android.wordcounter.R;
import com.xengar.android.wordcounter.utils.ActivityUtils;

import static com.xengar.android.wordcounter.utils.Constants.CURRENT_TEXT;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    private long cWords = 0;
    private long cCharacters = 0;
    private long cSpaces = 0;

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
            case R.id.action_quick_count:
                quickCount();
                return true;

            case R.id.action_share:
                // https://medium.com/google-developers/sharing-content-between-android-apps-2e6db9d1368b#.6usvw9n9p
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setText(getCurrentText())
                        .getIntent();
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
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
        calculateWords();
        // TODO: Use a layout.
        String message = "Words: " + cWords
                + "\nCharacters: " + cCharacters
                + "\nSpaces: " + cSpaces;

        AlertDialog alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle(R.string.quick_count)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                //.setView(colorPickerPalette)
                .setMessage(message)
                .create();
        alert.show();
    }

    /**
     * Get the number of words in the text.
     */
    private void calculateWords() {
        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        String text = textView.getText().toString();
        cCharacters = text.length();
        // Parse out unwanted whitespace so the split is accurate
        // Source https://github.com/Microsoft/vscode-wordcount/blob/ae44cafd7be4e22e38e7afc6996e1646b5366c20/extension.ts
        // docContent = docContent.replace(/(< ([^>]+)<)/g, '').replace(/\s+/g, ' ');
        // docContent = docContent.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
        text = text.replaceAll("(< ([^>]+)<)", "").replaceAll("\\s+", " ");
        text = text.replaceFirst("^\\s\\s*", "").replaceFirst("\\s\\s*$", "");
        cSpaces = cCharacters - text.length();

        // if the word contains at least one character of the preferences, count it as word
        String[] words =  text.split(" ");
        String characters = ActivityUtils.getPreferenceCharactersInWord(getApplicationContext());
        cSpaces += (words.length > 0)? words.length - 1 : 0;
        cWords = 0;
        for (String word: words) {
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (characters.indexOf(ch) > -1) {
                    cWords++;
                    break;
                }
            }
        }
    }
}
