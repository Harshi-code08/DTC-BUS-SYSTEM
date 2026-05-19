package com.dtc.bus.system.service;


import java.sql.SQLException;
import java.util.List;

import com.dtc.bus.system.dao.RouteDAO;
import com.dtc.bus.system.dto.RouteDTO;

public class RouteService {

    private final RouteDAO routeDAO = new RouteDAO();

    public int addRoute(RouteDTO route) throws SQLException {
        validate(route);
        return routeDAO.addRoute(route);
    }

    public List<RouteDTO> getAllRoutes() throws SQLException {
        return routeDAO.getAllRoutes();
    }

    public RouteDTO getRouteById(int id) throws SQLException {
        RouteDTO route = routeDAO.getRouteById(id);
        if (route == null) throw new IllegalArgumentException("Route not found for id: " + id);
        return route;
    }

    public List<RouteDTO> searchRoutes(String keyword) throws SQLException {
        if (keyword == null || keyword.trim().length() < 2)
            throw new IllegalArgumentException("Search keyword must be at least 2 characters.");
        return routeDAO.searchRoutes(keyword.trim());
    }

    public boolean updateRoute(RouteDTO route) throws SQLException {
        if (route.getId() <= 0) throw new IllegalArgumentException("Invalid route id.");
        validate(route);
        return routeDAO.updateRoute(route);
    }

    public boolean deleteRoute(int id) throws SQLException {
        return routeDAO.deleteRoute(id);
    }

    private void validate(RouteDTO r) {
        if (r.getRouteNumber() == null || r.getRouteNumber().trim().isEmpty())
            throw new IllegalArgumentException("Route number is required.");
        if (r.getSourceStop() == null || r.getSourceStop().trim().isEmpty())
            throw new IllegalArgumentException("Source stop is required.");
        if (r.getDestinationStop() == null || r.getDestinationStop().trim().isEmpty())
            throw new IllegalArgumentException("Destination stop is required.");
        if (r.getFare() < 0)
            throw new IllegalArgumentException("Fare cannot be negative.");
    }
}
