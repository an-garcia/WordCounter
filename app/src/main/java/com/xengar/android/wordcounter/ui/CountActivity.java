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
import android.view.Menu;
import android.view.MenuItem;

import com.xengar.android.wordcounter.R;
import com.xengar.android.wordcounter.utils.ActivityUtils;

import static com.xengar.android.wordcounter.utils.Constants.CURRENT_TEXT;

/**
 * CountActivity
 */
public class CountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.count, menu);
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
            case R.id.action_search:
                // TODO
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
        //MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.text);
        //return textView.getText().toString();
        return null;
    }

}
