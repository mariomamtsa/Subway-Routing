package me.subwayrouting.model.route;
import java.util.*;

public class Route {
    private String name;
    private Map<String, Station> stations;

    public Route(String name) {
        this.name = name;
        this.stations = new LinkedHashMap<>();
    }

    public void addStation(Station station) {
        stations.put(station.getName(), station);
    }

    public String getName() {
        return name;
    }

    public Map<String, Station> getStations() {
        return Collections.unmodifiableMap(stations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return name.equals(route.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
