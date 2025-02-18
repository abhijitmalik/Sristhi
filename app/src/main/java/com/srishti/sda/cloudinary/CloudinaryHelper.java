package com.srishti.sda.cloudinary;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryHelper {
    private static final String TAG = "CloudinaryHelper";
    private static final String CLOUD_NAME = "dwbnwxau1";
    private static final String API_KEY = "547597453552983";
    private static final String API_SECRET = "VcQ7oL5dyTUMduh71HdJpgoWeSo";

    public static void init(Context context) {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        MediaManager.init(context, config);
        Log.d(TAG, "Cloudinary Initialized");
    }

    public static void uploadImage(Context context, Uri imageUri, CloudinaryUploadCallback callback) {
        File file = new File(imageUri.getPath());
        Log.d(TAG, "Starting Image Upload: " + file.getPath());

        new Thread(() -> {
            try {
                Cloudinary cloudinary = MediaManager.get().getCloudinary();
                Map result = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "android_uploads"));
                String publicId = (String) result.get("public_id");
                String imageUrl = (String) result.get("secure_url");

                Log.d(TAG, "Upload Success: " + imageUrl);
                callback.onSuccess(publicId, imageUrl);
            } catch (Exception e) {
                Log.e(TAG, "Upload Failed: " + e.getMessage());
                callback.onFailure(e.getMessage());
            }
        }).start();
    }

    public interface CloudinaryUploadCallback {
        void onSuccess(String publicId, String imageUrl);
        void onFailure(String errorMessage);
    }
}
