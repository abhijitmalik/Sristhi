package com.srishti.sda.cloudinary;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.srishti.sda.R;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CloudinaryActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText imageIdEditText;
    private Button pickImageButton, uploadImageButton, loadImageButton;
    private Uri selectedImageUri;
    private String uploadedImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudinary);

        imageView = findViewById(R.id.imageView);
        imageIdEditText = findViewById(R.id.imageIdEditText);
        pickImageButton = findViewById(R.id.pickImageButton);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        loadImageButton = findViewById(R.id.loadImageButton);

        // Initialize Cloudinary
        CloudinaryHelper.init(this);

        // Open gallery when button is clicked
        pickImageButton.setOnClickListener(v -> openGallery());

        // Upload selected image
        uploadImageButton.setOnClickListener(v -> {
            if (imageView.getDrawable() != null) {
                // Get Bitmap from ImageView
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                // Convert Bitmap to File or ByteArray
                File imageFile = convertBitmapToFile(bitmap);

                if (imageFile != null) {
                    // Upload image to Cloudinary
                    CloudinaryHelper.uploadImage(this, Uri.fromFile(imageFile), new CloudinaryHelper.CloudinaryUploadCallback() {
                        @Override
                        public void onSuccess(String publicId, String imageUrl) {
                            uploadedImageId = publicId;
                            runOnUiThread(() -> {
                                imageIdEditText.setText(publicId);
                                Toast.makeText(CloudinaryActivity.this, "Upload Success!", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            runOnUiThread(() -> Toast.makeText(CloudinaryActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show());
                        }
                    });
                } else {
                    Toast.makeText(this, "Error converting image.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No Image Selected!", Toast.LENGTH_SHORT).show();
            }
        });





        // Load Image using ID
        loadImageButton.setOnClickListener(v -> {
            String imageId = imageIdEditText.getText().toString().trim();
            if (!imageId.isEmpty()) {
                String cloudinaryUrl = "https://res.cloudinary.com/dwbnwxau1/image/upload/" + imageId + ".jpg";
                Glide.with(CloudinaryActivity.this).load(cloudinaryUrl).into(imageView);
            } else {
                Toast.makeText(this, "Enter Image ID!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Open gallery using Intent
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private File convertBitmapToFile(Bitmap bitmap) {
        File file = null;
        try {
            file = File.createTempFile("image_", ".jpg", getCacheDir()); // Create temp file in cache dir
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // Compress Bitmap to JPEG
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // Handle image selection result
    private final androidx.activity.result.ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                selectedImageUri = result.getData().getData();
                                Glide.with(CloudinaryActivity.this).load(selectedImageUri).into(imageView);
                            }
                        }
                    });
}
