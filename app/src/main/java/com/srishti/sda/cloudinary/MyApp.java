package com.srishti.sda.cloudinary;


import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Cloudinary Configuration
        Map config = new HashMap();
        config.put("cloud_name", "dwbnwxau1");  // Replace with your Cloudinary cloud name
        config.put("api_key", "547597453552983");        // Replace with your Cloudinary API key
        config.put("api_secret", "VcQ7oL5dyTUMduh71HdJpgoWeSo");  // Replace with your Cloudinary API secret

        MediaManager.init(this, config);
    }
}
