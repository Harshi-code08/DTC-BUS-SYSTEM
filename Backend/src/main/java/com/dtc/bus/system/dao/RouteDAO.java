package com.dtc.bus.system.dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dtc.bus.system.dto.RouteDTO;
import com.dtc.bus.system.util.DBConnection;

public class RouteDAO {

    private static final String INSERT =
        "INSERT INTO routes(route_number,route_name,source_stop,destination_stop," +
        "total_distance_km,fare,total_stops,is_active) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_ALL =
        "SELECT * FROM routes";
    private static final String SELECT_BY_ID =
        "SELECT * FROM routes WHERE id=?";
    private static final String SEARCH =
        "SELECT * FROM routes WHERE source_stop LIKE ? OR destination_stop LIKE ? OR route_name LIKE ?";
    private static final String UPDATE =
        "UPDATE routes SET route_number=?,route_name=?,source_stop=?,destination_stop=?," +
        "total_distance_km=?,fare=?,total_stops=?,is_active=? WHERE id=?";
    private static final String DELETE =
        "DELETE FROM routes WHERE id=?";

    public int addRoute(RouteDTO route) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, route.getRouteNumber());
            ps.setString(2, route.getRouteName());
            ps.setString(3, route.getSourceStop());
            ps.setString(4, route.getDestinationStop());
            ps.setDouble(5, route.getTotalDistanceKm());
            ps.setDouble(6, route.getFare());
            ps.setInt(7, route.getTotalStops());
            //ps.setString(8, route.getStops());
            ps.setBoolean(8, route.isActive());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    public List<RouteDTO> getAllRoutes() throws SQLException {
        List<RouteDTO> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public RouteDTO getRouteById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public List<RouteDTO> searchRoutes(String keyword) throws SQLException {
        List<RouteDTO> list = new ArrayList<>();
        String k = "%" + keyword + "%";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH)) {
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public boolean updateRoute(RouteDTO route) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, route.getRouteNumber());
            ps.setString(2, route.getRouteName());
            ps.setString(3, route.getSourceStop());
            ps.setString(4, route.getDestinationStop());
            ps.setDouble(5, route.getTotalDistanceKm());
            ps.setDouble(6, route.getFare());
            ps.setInt(7, route.getTotalStops());
           // ps.setString(8, route.getStops());
            ps.setBoolean(8, route.isActive());
            ps.setInt(9, route.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRoute(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private RouteDTO mapRow(ResultSet rs) throws SQLException {
        return new RouteDTO(
            rs.getInt("id"),
            rs.getString("route_number"),
            rs.getString("route_name"),
            rs.getString("source_stop"),
            rs.getString("destination_stop"),
            rs.getDouble("total_distance_km"),
            rs.getDouble("fare"),
            rs.getInt("total_stops"),
            rs.getBoolean("is_active")
        );
    }
}

