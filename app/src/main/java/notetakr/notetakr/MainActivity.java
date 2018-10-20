package notetakr.notetakr;

import android.content.Intent;
import android.graphics.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.camerakit.CameraKitView;
import com.camerakit.CameraKitView.ImageCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private CameraKitView camera;
    private ImageButton capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = findViewById(R.id.camera);
        capture = findViewById(R.id.button_capture);

        capture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                camera.captureImage(new ImageCallback() {

                    @Override
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

                        textRecognizer.processImage(image)

                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText result) {
                                String resultText = result.getText();
                                System.out.println(resultText);

                                Intent editIntent = new Intent(Intent.ACTION_EDIT);
                                editIntent.putExtra("data", resultText);
                                startActivity(editIntent);
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                // handle errors
                            }
                        });

                    }

                });

            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        camera.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.onResume();
    }

    @Override
    protected void onPause() {
        camera.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        camera.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
