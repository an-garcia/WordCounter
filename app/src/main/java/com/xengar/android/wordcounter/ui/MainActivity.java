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

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        String currentText = ActivityUtils.getCurrentText(getApplicationContext());
        if (currentText != null){
            textView.setText(currentText);
            ActivityUtils.saveStringToPreferences(getApplicationContext(), CURRENT_TEXT, null);
        }

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
        if (id == R.id.action_settings) {
            MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
            String currentText = textView.getText().toString();
            ActivityUtils.saveStringToPreferences(getApplicationContext(), CURRENT_TEXT, currentText);
            ActivityUtils.launchSettingsActivity(getApplicationContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
