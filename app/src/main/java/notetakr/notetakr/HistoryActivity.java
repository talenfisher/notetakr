package notetakr.notetakr;

import android.content.Context;
import android.content.Intent;
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
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

public class HistoryActivity extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        EditText tv = (EditText)findViewById(R.id.history_textbox);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File file = new File(getFilesDir(), "noteTakr.txt");
        
        try {
            String content = new Scanner(file).useDelimiter("\\Z").next();
            tv.setText(content);
        } catch (Exception e){
            Alert alert = new Alert(this, e.toString());
        }

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.History_Options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String selectedItem = parent.getItemAtPosition(position).toString();
                 switch(selectedItem.trim()) {

                     case "Clear All":
                         try {
                             File file = new File(getFilesDir(), "noteTakr.txt");
                             Writer writer = new FileWriter(file, false);
                             writer.write("");
                             writer.close();

                         } catch(Exception e) {
                             Alert alert = new Alert(context, e.toString());
                         }

                         tv.setText("");
                         mySpinner.setSelection(0);
                         break;

                     case "Save":
                         try {
                             File file = new File(getFilesDir(), "noteTakr.txt");
                             Writer writer = new FileWriter(file, false);
                             writer.write(tv.getText().toString());
                             Alert alert = new Alert(context, "Saved"); alert.show();
                             writer.close();

                         } catch (Exception e) {
                             Alert alert = new Alert(context, e.toString()); alert.show();
                         }

                         mySpinner.setSelection(0);
                         break;

                     case "Export":
                         Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                         sharingIntent.setType("text/plain");
                         sharingIntent.putExtra(Intent.EXTRA_TITLE, "title");
                         sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv.getText());
                         startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));

                         mySpinner.setSelection(0);
                         break;


                 }
             }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
         });
    }

}
