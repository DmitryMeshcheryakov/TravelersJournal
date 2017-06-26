package by.insight.travelersjournal.app;

import android.app.Application;

import by.insight.travelersjournal.database.RealmModuleTravelAndEvent;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();

    }

    private void initRealmConfiguration()
    {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("travel.realm")
                .modules(new RealmModuleTravelAndEvent())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
