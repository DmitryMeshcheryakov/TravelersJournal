package by.insight.travelersjournal.view.activity.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



public abstract class BaseActivity extends AppCompatActivity {

    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
