package ex.nme.hallplatsen.models;

/**
 * Created by nm2 on 2017-07-25
 */

public class Station {

    private String id;
    private String name;


    public Station(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
