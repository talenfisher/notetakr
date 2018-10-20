package notetakr.notetakr;

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
import android.widget.EditText;


public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Get transcription from previous activity
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        EditText tv = (EditText)findViewById(R.id.content_edit);
        tv.setText(data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        TextInputEditText title = (TextInputEditText) findViewById(R.id.title_bar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TITLE, title.getText());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv.getText());
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
            }
        });
    }
}
