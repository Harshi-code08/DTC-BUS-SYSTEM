package com.dtc.bus.system.dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dtc.bus.system.dto.ScheduleDTO;
import com.dtc.bus.system.util.DBConnection;

public class ScheduleDAO {

    private static final String INSERT =
        "INSERT INTO schedules(route_id,bus_id,departure_time,arrival_time,schedule_days,is_active) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_ALL =
        "SELECT * FROM schedules";
    private static final String SELECT_BY_ID =
        "SELECT * FROM schedules WHERE id=?";
    private static final String UPDATE =
        "UPDATE schedules SET route_id=?,bus_id=?,departure_time=?,arrival_time=?,schedule_days=?,is_active=? WHERE id=?";
    private static final String DELETE =
        "DELETE FROM schedules WHERE id=?";

    public int addSchedule(ScheduleDTO s) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getRouteId());
            ps.setInt(2, s.getBusId());
            ps.setString(3, s.getDepartureTime());
            ps.setString(4, s.getArrivalTime());
            ps.setString(5, s.getScheduleDays());
            ps.setBoolean(6, s.isActive());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public List<ScheduleDTO> getAllSchedules() throws SQLException {
        List<ScheduleDTO> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public ScheduleDTO getScheduleById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public boolean updateSchedule(ScheduleDTO s) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setInt(1, s.getRouteId());
            ps.setInt(2, s.getBusId());
            ps.setString(3, s.getDepartureTime());
            ps.setString(4, s.getArrivalTime());
            ps.setString(5, s.getScheduleDays());
            ps.setBoolean(6, s.isActive());
            ps.setInt(7, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteSchedule(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private ScheduleDTO mapRow(ResultSet rs) throws SQLException {
        return new ScheduleDTO(
            rs.getInt("id"),
            rs.getInt("route_id"),
            rs.getInt("bus_id"),
            rs.getString("departure_time"),
            rs.getString("arrival_time"),
            rs.getString("schedule_days"),
            rs.getBoolean("is_active")
        );
    }
}
