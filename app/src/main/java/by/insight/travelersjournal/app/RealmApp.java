package by.insight.travelersjournal.app;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;


import by.insight.travelersjournal.database.RealmModule;


import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
        Iconify.with(new MaterialModule());
    }

    private void initRealmConfiguration()
    {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("travel.realm")
                .modules(new RealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
