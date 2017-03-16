package com.example.whatsmymood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PhotoController {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private String compressedPhoto;
    private Bitmap decodedPhoto;

    public PhotoController() {
    }

    public String encodePhoto(Bitmap photo) {

        photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        this.compressedPhoto = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return compressedPhoto;
    }

    public Bitmap decodePhoto(String photo) {
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        this.decodedPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return this.decodedPhoto;
    }
}
