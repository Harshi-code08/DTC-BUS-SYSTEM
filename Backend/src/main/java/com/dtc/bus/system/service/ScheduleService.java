package com.dtc.bus.system.service;



import java.sql.SQLException;
import java.util.List;

import com.dtc.bus.system.dao.ScheduleDAO;
import com.dtc.bus.system.dto.ScheduleDTO;

public class ScheduleService {

    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public int addSchedule(ScheduleDTO s) throws SQLException {
        validate(s);
        return scheduleDAO.addSchedule(s);
    }

    public List<ScheduleDTO> getAllSchedules() throws SQLException {
        return scheduleDAO.getAllSchedules();
    }

    public ScheduleDTO getScheduleById(int id) throws SQLException {
        ScheduleDTO s = scheduleDAO.getScheduleById(id);
        if (s == null) throw new IllegalArgumentException("Schedule not found for id: " + id);
        return s;
    }

    public boolean updateSchedule(ScheduleDTO s) throws SQLException {
        if (s.getId() <= 0) throw new IllegalArgumentException("Invalid schedule id.");
        validate(s);
        return scheduleDAO.updateSchedule(s);
    }

    public boolean deleteSchedule(int id) throws SQLException {
        return scheduleDAO.deleteSchedule(id);
    }

    private void validate(ScheduleDTO s) {
        if (s.getRouteId() <= 0) 
        	throw 
        	new IllegalArgumentException("Valid route id is required.");
        if (s.getBusId() <= 0)   throw new IllegalArgumentException("Valid bus id is required.");
        if (s.getDepartureTime() == null || s.getDepartureTime().isEmpty())
            throw new IllegalArgumentException("Departure time is required.");
        if (s.getArrivalTime() == null || s.getArrivalTime().isEmpty())
            throw new IllegalArgumentException("Arrival time is required.");
    }
}

