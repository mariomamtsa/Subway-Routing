package me.subwayrouting;
import me.subwayrouting.model.route.Route;
import me.subwayrouting.model.route.Station;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;



public class Util {
    /**
     * Parse route station file in following format:<br>
     * {ROUTE_NAME_1}<br>
     * {TAB}{STATION_NAME_1}<br>
     * {TAB}{STATION_NAME_2}<br>
     * {TAB}{STATION_NAME_N}<br>
     * {ROUTE_NAME_2}<br>
     * ...
     *
     * @param file file to parse
     * @return set of routes with stations
     */
    public static Set<Route> parseRoutes(File file) {
        Set<Route> routeSet = new HashSet<>();
        Route route = null;

        try {
            for (String line : Files.readAllLines(file.toPath())) {
                if (line.startsWith("\u0009")) { // new station
                    String name = line.substring(1);
                    route.addStation(new Station(name.trim()));
                } else { // new route
                    if (route != null) routeSet.add(route);
                    route = new Route(line.trim());
                }
            }
            if (route != null) routeSet.add(route);
            return routeSet;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to parse routes.", ex);
        }
    }

}
