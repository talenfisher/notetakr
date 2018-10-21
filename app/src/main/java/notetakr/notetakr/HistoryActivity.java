package notetakr.notetakr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.util.Scanner;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.History_Options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        File file = new File(getFilesDir(), "noteTakr.txt");
        try {
            String content = new Scanner(file).useDelimiter("\\Z").next();
            EditText tv = (EditText)findViewById(R.id.history_textbox);
            tv.setText(content);
        } catch (Exception e){
            Alert alert = new Alert(this, e.toString());
        }
    }

}
