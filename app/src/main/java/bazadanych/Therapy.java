package bazadanych;

import java.util.Date;

/**
 * Created by dawid on 16.11.2016.
 */

public class Therapy {

    private long id;
    private long patientId;
    private Date startDate;
    private Date endDate;
    private boolean checked;

    public Therapy(long id, long patientId, Date startDate, Date endDate) {
        this.id = id;
        this.patientId = patientId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
