package com.example.whatsmymood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

class PhotoController {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public PhotoController() {
    }

    /**
     * Encodes a photo to a string to upload to Elastic Search
     * @param photo Bitmap photo from the camera activity
     * @return Returns an encoded string
     */
    public String encodePhoto(Bitmap photo) {

        photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Decodes a string from elastic search into a bitmap photo
     * @param photo Encoded string of a photo
     * @return Returns a bitmap photo for display
     */
    public Bitmap decodePhoto(String photo) {
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
