package by.insight.travelersjournal.model;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



public class Event extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private String descriptions;

    private Long date;

    private String time;

    private RealmList<ImageEvent> imageEvent;

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public RealmList<ImageEvent> getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(RealmList<ImageEvent> imageEvent) {
        this.imageEvent = imageEvent;
    }
}
