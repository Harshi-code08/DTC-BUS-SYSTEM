package com.dtc.bus.system.dao;

import com.dtc.bus.system.util.DBConnection;

import com.dtc.bus.system.dto.BusDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    private static final String INSERT =
        "INSERT INTO buses(bus_number,bus_type,capacity,driver_name,driver_contact,is_active) VALUES(?,?,?,?,?,?)";
    private static final String SELECT_ALL =
        "SELECT * FROM buses";
    private static final String SELECT_BY_ID =
        "SELECT * FROM buses WHERE id=?";
    private static final String UPDATE =
        "UPDATE buses SET bus_number=?,bus_type=?,capacity=?,driver_name=?,driver_contact=?,is_active=? WHERE id=?";
    private static final String DELETE =
        "DELETE FROM buses WHERE id=?";
    
    

    // ── CREATE ──────────────────────────────────────────────────
    public int addBus(BusDTO bus) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, bus.getBusNumber());
            ps.setString(2, bus.getBusType());
            ps.setInt(3, bus.getCapacity());
            ps.setString(4, bus.getDriverName());
            ps.setString(5, bus.getDriverContact());
            ps.setBoolean(6, bus.isActive());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    // ── READ ALL ────────────────────────────────────────────────
    public List<BusDTO> getAllBuses() throws SQLException {
        List<BusDTO> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // ── READ ONE ────────────────────────────────────────────────
    public BusDTO getBusById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    // ── UPDATE ──────────────────────────────────────────────────
    public boolean updateBus(BusDTO bus) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, bus.getBusNumber());
            ps.setString(2, bus.getBusType());
            ps.setInt(3, bus.getCapacity());
            ps.setString(4, bus.getDriverName());
            ps.setString(5, bus.getDriverContact());
            ps.setBoolean(6, bus.isActive());
            ps.setInt(7, bus.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ── DELETE ──────────────────────────────────────────────────
    public boolean deleteBus(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── MAP ROW ─────────────────────────────────────────────────
    private BusDTO mapRow(ResultSet rs) throws SQLException {
        return new BusDTO(
            rs.getInt("id"),
            rs.getString("bus_number"),
            rs.getString("bus_type"),
            rs.getInt("capacity"),
            rs.getString("driver_name"),
            rs.getString("driver_contact"),
            rs.getBoolean("is_active")
        );
    }
}

