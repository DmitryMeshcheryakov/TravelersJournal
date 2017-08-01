package by.insight.travelersjournal.tools;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import by.insight.travelersjournal.AppConstant;


public class SDUtils {


    public static String getRealPathFromUri(Context context, Uri uri)
    {
        Cursor cursor = null;
        String path = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = 0;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

//    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
//        Uri uri;
//        Cursor cursor;
//        int column_index_data;
//        int column_index_folder_name;
//        ArrayList<String> listOfAllImages = new ArrayList<String>();
//        String absolutePathOfImage = null;
//        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//        String[] projection = {MediaStore.MediaColumns.DATA,
//                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
//
//        cursor = activity.getContentResolver().query(uri, projection, null,
//                null, null);
//
//        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        column_index_folder_name = cursor
//                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//        while (cursor.moveToNext()) {
//            absolutePathOfImage = cursor.getString(column_index_data);
//
//            listOfAllImages.add(absolutePathOfImage);
//        }
//        return listOfAllImages;
//    }
}
