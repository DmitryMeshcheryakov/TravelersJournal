package by.insight.travelersjournal.tools;


import android.content.Intent;
import android.provider.MediaStore;

import by.insight.travelersjournal.AppConstant;

public class IntentUtils {

    public static Intent addImagesFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType(AppConstant.IMAGES);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    public static Intent addPhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }

}
