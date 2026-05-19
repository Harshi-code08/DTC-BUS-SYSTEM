package com.dtc.bus.system.service;



import java.sql.SQLException;
import java.util.List;

import com.dtc.bus.system.dao.BusDAO;
import com.dtc.bus.system.dto.BusDTO;

public class BusService {

    private final BusDAO busDAO = new BusDAO();

    public int addBus(BusDTO bus) throws SQLException {
        if (bus.getBusNumber() == null || bus.getBusNumber().trim().isEmpty())
            throw new IllegalArgumentException("Bus number is required.");
        if (bus.getBusType() == null || bus.getBusType().trim().isEmpty())
            throw new IllegalArgumentException("Bus type is required.");
        if (bus.getCapacity() <= 0)
            throw new IllegalArgumentException("Capacity must be positive.");
        return busDAO.addBus(bus);
    }

    public List<BusDTO> getAllBuses() throws SQLException {
        return busDAO.getAllBuses();
    }

    public BusDTO getBusById(int id) throws SQLException {
        BusDTO bus = busDAO.getBusById(id);
        if (bus == null) throw new IllegalArgumentException("Bus not found for id: " + id);
        return bus;
    }

    public boolean updateBus(BusDTO bus) throws SQLException {
        if (bus.getId() <= 0) throw new IllegalArgumentException("Invalid bus id.");
        return busDAO.updateBus(bus);
    }

    public boolean deleteBus(int id) throws SQLException {
        return busDAO.deleteBus(id);
    }
}
