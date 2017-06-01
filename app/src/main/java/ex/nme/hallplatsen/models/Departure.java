package ex.nme.hallplatsen.models;

/**
 * Created by nm2 on 2017-06-01.
 */

public class Departure {

    private String routeName;
    private String routeDirection;
    private String timeTo;

    public Departure(String routeName, String routeDirection, String timeTo) {
        this.routeName = routeName;
        this.routeDirection = routeDirection;
        this.timeTo = timeTo;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getRouteDirection() {
        return routeDirection;
    }

    public String getTimeToDeparture() {
        return timeTo;
    }
}
