package com.dtc.bus.system.dto;

public class ScheduleDTO {
    private int id;
    private int routeId;
    private int busId;
    private String departureTime;   // HH:mm
    private String arrivalTime;     // HH:mm
    private String scheduleDays;    // e.g. MON,TUE,WED
    private boolean isActive;

    public ScheduleDTO() {}

    public ScheduleDTO(int id, int routeId, int busId, String departureTime,
                       String arrivalTime, String scheduleDays, boolean isActive) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.scheduleDays = scheduleDays;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public void setId(int id) { 
    	this.id = id; 
    	}

    public int getRouteId() { 
    	return routeId; }
    
    public void setRouteId(int routeId) { 
    	this.routeId = routeId; 
    	}

    public int getBusId() { 
    	return busId; 
    	}
    public void setBusId(int busId) { 
    	this.busId = busId; 
    	}

    public String getDepartureTime() { 
    	return departureTime; 
    	}
    
    public void setDepartureTime(String departureTime) { 
    	this.departureTime = departureTime; 
    	}

    public String getArrivalTime() {
    	return arrivalTime; 
    	}
    public void setArrivalTime(String arrivalTime) {
    	this.arrivalTime = arrivalTime;
    	}

    public String getScheduleDays() { 
    	return scheduleDays; 
    	}
    public void setScheduleDays(String scheduleDays) { 
    	this.scheduleDays = scheduleDays;
    	}

    public boolean isActive() { 
    	return isActive; 
    	}
    public void setActive(boolean active) { 
    	isActive = active; 
    	}
}

