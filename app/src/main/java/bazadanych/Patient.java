package bazadanych;

/**
 * Created by dawid on 16.11.2016.
 */

public class Patient {

    private long id;
    private String name;
    private String surname;
    public boolean checked;


    public Patient(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        checked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChecked(){
        return checked;
    }



}
