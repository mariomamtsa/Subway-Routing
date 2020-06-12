package me.subwayrouting;

import me.subwayrouting.model.route.Route;
import me.subwayrouting.model.route.Station;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        // parse & prepare
        Set<Route> routeSet = Util.parseRoutes(new File("stations.txt"));
        Map<String, Station> stationMap = new HashMap<>();
        routeSet.forEach(r -> stationMap.putAll(r.getStations()));

        System.out.println("Enter source station name:");
        Station source = readStation(stationMap);

        System.out.println("Enter destination station name:");
        Station destination = readStation(stationMap);

        // processing
        PathFinder pathFinder = new PathFinder(routeSet);
        List<Station> path = pathFinder.findShortestPath(source, destination);

        // output
        if (path.isEmpty()) {
            System.out.println("No way to achieve destinaton :(");
        } else {
            System.out.println("Your best path:\n- " + path.stream().map(Station::getName)
                    .collect(Collectors.joining("\n- ")));
            System.out.println("\nTime: " + (path.size() - 1));
        }
    }

    /**
     * Read station by name from console
     *
     * @param stationMap station map
     * @return station
     */
    private static Station readStation(Map<String, Station> stationMap) {
        Scanner scanner = new Scanner(System.in);
        Station station = null;

        System.out.print("> ");
        while (station == null) {
            station = stationMap.get(scanner.nextLine());
            if (station == null)
                System.out.print("Station with that name not found. Please try again.\n> ");
        }

        return station;
    }

}
