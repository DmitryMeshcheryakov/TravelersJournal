package by.insight.travelersjournal.app;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import by.insight.travelersjournal.realm.SimpleRealmModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;


public class SimpleRealmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("travel.realm")
                .modules(new SimpleRealmModule())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
