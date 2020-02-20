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
package com.xengar.android.wordcounter.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.xengar.android.wordcounter.BuildConfig;
import com.xengar.android.wordcounter.R;
import com.xengar.android.wordcounter.utils.ActivityUtils;

/**
 * HelpActivity
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help, menu);
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
            case R.id.action_feedback:
                giveFeedback();
                return true;

            case R.id.action_problem:
                reportProblem();
                return true;

            case R.id.action_license:
                showLicense();
                return true;

            case R.id.action_version:
                showVersion();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Send feedback email.
     */
    private void reportProblem(){
        Intent sendMessage = new Intent(Intent.ACTION_SEND);
        sendMessage.setType("message/rfc822");
        sendMessage.putExtra(Intent.EXTRA_EMAIL, new String[]{
                getResources().getString(R.string.feedback_email)});
        sendMessage.putExtra(Intent.EXTRA_SUBJECT, "Word Counter Problem");
        sendMessage.putExtra(Intent.EXTRA_TEXT,
                getResources().getString(R.string.problem_message));
        try {
            startActivity(Intent.createChooser(sendMessage, "Report a problem"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Communication app not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Send feedback email.
     */
    private void giveFeedback(){
        Intent sendMessage = new Intent(Intent.ACTION_SEND);
        sendMessage.setType("message/rfc822");
        sendMessage.putExtra(Intent.EXTRA_EMAIL, new String[]{
                getResources().getString(R.string.feedback_email)});
        sendMessage.putExtra(Intent.EXTRA_SUBJECT, "Word Counter Feedback");
        sendMessage.putExtra(Intent.EXTRA_TEXT,
                getResources().getString(R.string.feedback_message));
        try {
            startActivity(Intent.createChooser(sendMessage, "Give Feedback"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Communication app not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show version information
     */
    private void showVersion() {
        //set up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.version_dialog);
        dialog.setCancelable(true);

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.version_number);
        text.setText(BuildConfig.VERSION_NAME);

        dialog.show();
    }

    /**
     * Show License information
     */
    private void showLicense() {
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        LayoutInflater inflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.license_dialog, null);
        layout.setMinimumWidth((int)(displayRectangle.width() * 0.75f));
        layout.setMinimumHeight((int)(displayRectangle.height() * 0.75f));

        //set up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(layout);
        dialog.setCancelable(true);

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.copyright);
        text.setText(ActivityUtils.fromHtml(getString(R.string.eula_string)));

        dialog.show();
    }

}
