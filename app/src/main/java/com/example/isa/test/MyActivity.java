package com.example.isa.test;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.isa.test.MESSAGE";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);
        setContentView(R.layout.example_open_page);
        ImageButton sharingButton = new ImageButton(this);
        sharingButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                shareIt();
                                            }
                                         });
                sharingButton.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        sharingButton.setImageResource(R.drawable.ic_action_share);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
//        getMenuInflater().inflate(R.menu.menu_my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_share:
                shareIt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void openSearch() {
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setView(R.layout.dialog_search).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent searchingIntent = new Intent(android.content.Intent.ACTION_SEND);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openSettings(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    /**called when the user clicks the share button */
    private void shareIt(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Share Content Body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Log.d("MyActivity", "sendMessage not working");
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        Log.d("MyActivity", "message: " + message);
    }

    /** Will save the fields entered in 'add new card' */
    public void saveCard(View view) {
        EditText phoneNum = (EditText) findViewById(R.id.phoneNum);

        String id = phoneNum.getText().toString();
        saveInfo(view);



        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        //setContentView(R.layout.example_open_page);

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, prefs.getString(id + "name", null) + " " + prefs.getString(id + "address", null)
                + " " + prefs.getString(id + "company", null)  + " " + prefs.getString(id + "email", null) + " " + prefs.getString(id + "phoneNum", null));
        startActivity(intent);
        Log.d("MyActivity", "message: " + prefs.getString(id + "name", null));

        /*String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            System.out.println(name);
            int idName = prefs.getInt("idName", 0); //0 is the default value.
        }*/
    }

    public void saveInfo (View view) {
        EditText name = (EditText) findViewById(R.id.name);
        EditText address = (EditText) findViewById(R.id.address);
        EditText company = (EditText) findViewById(R.id.company);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phoneNum = (EditText) findViewById(R.id.phoneNum);

        String id = phoneNum.getText().toString();
        //public static final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(id + "name", name.getText().toString());
        editor.putString(id + "address", address.getText().toString());
        editor.putString(id + "company", company.getText().toString());
        editor.putString(id + "email", email.getText().toString());
        editor.putString(id + "phoneNum", phoneNum.getText().toString());
        editor.commit();
    }

    public void editCard(View view) {
        String id = "123";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        EditText name = (EditText) findViewById(R.id.name);
        EditText address = (EditText) findViewById(R.id.address);
        EditText company = (EditText) findViewById(R.id.company);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phoneNum = (EditText) findViewById(R.id.phoneNum);

        name.setText(prefs.getString(id + "name", null));
        address.setText(prefs.getString(id + "address", null));
        company.setText(prefs.getString(id + "company", null));
        email.setText(prefs.getString(id + "email", null));
        phoneNum.setText(prefs.getString(id + "phoneNum", null));

        saveInfo(view);
    }

    public void openForm(View view) {
        setContentView(R.layout.dialog_form);
    }

}
