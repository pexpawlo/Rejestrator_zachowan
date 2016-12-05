package bazadanych;

import java.util.Date;

/**
 * Created by dawid on 16.11.2016.
 */

public class Event {

    private long patientId;
    private long id;
    private Date date;

    public Event(long id, long patientId, Date date) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
