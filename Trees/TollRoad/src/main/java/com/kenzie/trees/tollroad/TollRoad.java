package com.kenzie.trees.tollroad;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Application to demonstrate a TreeMap storing key:value pairs in sorted
 * order based on natural ordering of keys and also a different ordering
 * of the same keys based on the results of a Comparator.
 */
public class TollRoad {
    private final Map<String, Vehicle> descriptionToVehicle;
    private final Map<Vehicle, Object> stateToVehicle;

    /**
     * TollRoad constructor creates two TreeMaps to store data with
     * different keys. The second uses a Comparator to store based
     * on the state code of each vehicle.
     */
    public TollRoad() {
        this.descriptionToVehicle = new TreeMap<>();
        this.stateToVehicle = new TreeMap<>(new SortStateLicense());
    }

    /**
     * Adds a toll with the given vehicle's description. If the vehicle has
     * already passed through a toll reader, its toll count is updated.
     * If this is the first time the vehicle has passed through a toll
     * reader, a new Vehicle object is created and added to the TreeMaps.
     *
     * @param description The Vehicle's description.
     */
    public void addToll(String description) {
        if (descriptionToVehicle.containsKey(description)) {
            descriptionToVehicle.get(description).addToll();
        } else {
            Vehicle newCar = new Vehicle(description);
            descriptionToVehicle.put(newCar.description, newCar);
            stateToVehicle.put(newCar, new SortStateLicense());
        }
    }

    /**
     * Builds and returns a String containing the current Vehicles
     * sorted by description.
     *
     * @return String containing the current vehicles, sorted by description.
     */
    public String getVehicleReportByDescription() {
        StringBuilder sb = new StringBuilder();
        for (Vehicle v : this.descriptionToVehicle.values()) {
            sb.append(v);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Builds and returns a String containing the current Vehicles
     * sorted by state.
     *
     * @return String containing the current vehicles, sorted by state.
     */
    public String getVehicleReportByState() {
        StringBuilder sb = new StringBuilder();
        for (Vehicle v : this.stateToVehicle.keySet()) {
            sb.append(v);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Main method to demonstrate the Toll Road and Vehicle classes.
     *
     * Expected output:
     *
     * Vehicle Tolls By Description:
     * Description: CCO123ABC, Toll Count: 2
     * Description: CIA432LMN, Toll Count: 3
     * Description: SFL456DEF, Toll Count: 2
     * Description: TCO789XYZ, Toll Count: 4
     * Description: TIA765QRS, Toll Count: 1
     *
     * Vehicle Tolls By State:
     * Description: CCO123ABC, Toll Count: 2
     * Description: TCO789XYZ, Toll Count: 4
     * Description: SFL456DEF, Toll Count: 2
     * Description: CIA432LMN, Toll Count: 3
     * Description: TIA765QRS, Toll Count: 1
     *
     * @param args Command line arguments; ignored in this application.
     */
    public static void main(String[] args) {
        // Create the TollRoad, add some test data, and show the results.
        TollRoad tollRoad = new TollRoad();
        tollRoad.addToll("CCO123ABC");
        tollRoad.addToll("CIA432LMN");
        tollRoad.addToll("TCO789XYZ");
        tollRoad.addToll("SFL456DEF");
        tollRoad.addToll("TCO789XYZ");
        tollRoad.addToll("CIA432LMN");
        tollRoad.addToll("TCO789XYZ");
        tollRoad.addToll("TIA765QRS");
        tollRoad.addToll("CCO123ABC");
        tollRoad.addToll("SFL456DEF");
        tollRoad.addToll("CIA432LMN");
        tollRoad.addToll("TCO789XYZ");

        System.out.println("Vehicle Tolls By Description:");
        System.out.println(tollRoad.getVehicleReportByDescription());

        System.out.println("Vehicle Tolls By State:");
        System.out.println(tollRoad.getVehicleReportByState());
    }
}

class SortStateLicense implements Comparator<Vehicle> {
    public int compare(Vehicle car1, Vehicle car2) {
        return car1.description.substring(1)
                .compareTo(car2.description.substring(1));
    }
}
