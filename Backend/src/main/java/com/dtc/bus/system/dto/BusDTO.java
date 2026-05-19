package com.dtc.bus.system.dto;


public class BusDTO {
    private int id;
    private String busNumber;
    private String busType;       // AC, NON_AC, ELECTRIC
    private int capacity;
    private String driverName;
    private String driverContact;
    private boolean isActive;

    public BusDTO() {}

    public BusDTO(int id, String busNumber, String busType, int capacity,
                  String driverName, String driverContact, boolean isActive) {
        this.id = id;
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.driverName = driverName;
        this.driverContact = driverContact;
        this.isActive = isActive;
    }

    public int getId() { 
        return id; 
        }

    public void setId(int id) { 
        this.id = id; 
        }

    public String getBusNumber() {
         return busNumber; 
         }

    public void setBusNumber(String busNumber) {
         this.busNumber = busNumber; 
         }

    public String getBusType() {
         return busType; 
         }

    public void setBusType(String busType) { 
        this.busType = busType; }

    public int getCapacity() {
         return capacity; 
         }

    public void setCapacity(int capacity) {
         this.capacity = capacity; 
         }

    public String getDriverName() {
         return driverName; 
         }
         
    public void setDriverName(String driverName) {
         this.driverName = driverName;
         }

    public String getDriverContact() {
         return driverContact; 
         }

    public void setDriverContact(String driverContact) {
         this.driverContact = driverContact; 
         }

    public boolean isActive() { 
        return isActive; 
        }

    public void setActive(boolean active) { 
        isActive = active; 
        }
}
