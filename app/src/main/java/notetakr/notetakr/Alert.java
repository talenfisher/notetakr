package notetakr.notetakr;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Alert {
    private Context context;
    private String message;

    public Alert(Context context, String message) {
        this.context = context;
        this.message = message;    
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage(this.message);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int x) {}
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
