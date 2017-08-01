package by.insight.travelersjournal.view.activity.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import by.insight.travelersjournal.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



public abstract class BaseActivity extends AppCompatActivity {

    private static final int RC_READ_EXTERNAL_STORAGE_READ = 123;
    private static final int RC_CAMERA = 124;
    private static final int RC_WRITE_EXTERNAL_STORAGE_READ = 125;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionsExternalStorageRead();
        permissionsExternalStorageWrite();
        permissionsCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE_READ)
    public void permissionsExternalStorageRead() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_gallery),
                    RC_READ_EXTERNAL_STORAGE_READ,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE_READ)
    public void permissionsExternalStorageWrite() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_gallery),
                    RC_WRITE_EXTERNAL_STORAGE_READ,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(RC_CAMERA)
    public void permissionsCamera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {

        } else {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.permissions_camera),
                    RC_CAMERA,
                    Manifest.permission.CAMERA);
        }
    }

}
