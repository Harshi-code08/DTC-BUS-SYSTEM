package com.dtc.bus.system.dto;


public class RouteDTO {
    private int id;
    private String routeNumber;
    private String routeName;
    private String sourceStop;
    private String destinationStop;
    private double totalDistanceKm;
    private double fare;
    private int totalStops;
    // comma-separated stop names
    private boolean isActive;

    public RouteDTO() {}

    public RouteDTO(int id, String routeNumber, String routeName, String sourceStop,
                    String destinationStop, double totalDistanceKm, double fare,
                    int totalStops, boolean isActive) {
        this.id = id;
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.sourceStop = sourceStop;
        this.destinationStop = destinationStop;
        this.totalDistanceKm = totalDistanceKm;
        this.fare = fare;
        this.totalStops = totalStops;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRouteNumber() { return routeNumber; }
    public void setRouteNumber(String routeNumber) { this.routeNumber = routeNumber; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getSourceStop() { return sourceStop; }
    public void setSourceStop(String sourceStop) { this.sourceStop = sourceStop; }

    public String getDestinationStop() { return destinationStop; }
    public void setDestinationStop(String destinationStop) { this.destinationStop = destinationStop; }

    public double getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(double totalDistanceKm) { this.totalDistanceKm = totalDistanceKm; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public int getTotalStops() { return totalStops; }
    public void setTotalStops(int totalStops) { this.totalStops = totalStops; }


    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
