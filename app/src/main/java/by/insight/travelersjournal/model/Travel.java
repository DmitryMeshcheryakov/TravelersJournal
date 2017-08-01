package by.insight.travelersjournal.model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Travel extends RealmObject {
    @PrimaryKey
    private String id;

    private String title;

    private String descriptions;

    private String imagePath;

    private RealmList<DayEvents> events;

    public Travel() {
    }

    public Travel(String title, String descriptions, String imagePath) {
        this.title = title;
        this.descriptions = descriptions;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public RealmList<DayEvents> getDayEvents() {
        return events;
    }

    public void setDayEvents(RealmList<DayEvents> events) {
        this.events = events;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
