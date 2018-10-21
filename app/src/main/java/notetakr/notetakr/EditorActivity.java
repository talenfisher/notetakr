package notetakr.notetakr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
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
import java.util.Date;


public class EditorActivity extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Get transcription from previous activity
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        EditText tv = (EditText)findViewById(R.id.content_edit);
        tv.setText(data);

        TextInputEditText title = (TextInputEditText) findViewById(R.id.title_bar);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch(selectedItem) {
                    case "Save Internal":
                        writeFile(title.getText().toString(), data);
                        Alert alert = new Alert(context, getFilesDir().getAbsolutePath() + "noteTakr.txt"); alert.show();
                        break;

                    case "Save External":
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(Intent.EXTRA_TITLE, title.getText().toString());
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv.getText());
                        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
                        break;

                    default: break;
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(EditorActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Save));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
    private void writeFile(String title, String s){
        File file = new File(getFilesDir(), "noteTakr.txt");
        try {
            Writer writer = new FileWriter(file, true);
            Date date = new Date();
            writer.write("---------------------\nDate: " + date.toString() + " Title: " + title + "\n\n" + s);
            writer.close();
        } catch (Exception e){
            Alert alert = new Alert(context, e.toString());
            alert.show();
        }
    }
}
