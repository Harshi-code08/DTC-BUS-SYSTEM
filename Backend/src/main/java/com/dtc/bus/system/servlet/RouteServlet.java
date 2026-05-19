package com.dtc.bus.system.servlet;



import com.dtc.bus.system.dto.RouteDTO;
import com.dtc.bus.system.service.RouteService;
import com.dtc.bus.system.util.JsonUtil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * GET    /api/routes              → all
 * GET    /api/routes?id=1         → by id
 * GET    /api/routes?search=ISBT  → search
 * POST   /api/routes              → add
 * PUT    /api/routes              → update
 * DELETE /api/routes?id=1         → delete
 */
@WebServlet("/api/routes")
public class RouteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    private final RouteService routeService = new RouteService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String idParam     = req.getParameter("id");
            String searchParam = req.getParameter("search");

            if (idParam != null) {
                RouteDTO route = routeService.getRouteById(Integer.parseInt(idParam));
                JsonUtil.sendJson(res, routeToJson(route).toJSONString());
            } else if (searchParam != null) {
                List<RouteDTO> routes = routeService.searchRoutes(searchParam);
                JSONArray arr = new JSONArray();
                routes.forEach(r -> arr.add(routeToJson(r)));
                JsonUtil.sendJson(res, arr.toJSONString());
            } else {
                List<RouteDTO> routes = routeService.getAllRoutes();
                JSONArray arr = new JSONArray();
                routes.forEach(r -> arr.add(routeToJson(r)));
                JsonUtil.sendJson(res, arr.toJSONString());
            }
        } catch (Exception e) {
            JsonUtil.sendError(res, 500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            RouteDTO route = parseBody(req);
            int id = routeService.addRoute(route);
            JsonUtil.sendJson(res, "{\"id\":" + id + ",\"message\":\"Route added successfully\"}");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            RouteDTO route = parseBody(req);
            routeService.updateRoute(route);
            JsonUtil.sendSuccess(res, "Route updated successfully");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            routeService.deleteRoute(id);
            JsonUtil.sendSuccess(res, "Route deleted successfully");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject routeToJson(RouteDTO r) {
        JSONObject o = new JSONObject();
        o.put("id", r.getId());
        o.put("routeNumber", r.getRouteNumber());
        o.put("routeName", r.getRouteName());
        o.put("sourceStop", r.getSourceStop());
        o.put("destinationStop", r.getDestinationStop());
        o.put("totalDistanceKm", r.getTotalDistanceKm());
        o.put("fare", r.getFare());
        o.put("totalStops", r.getTotalStops());
        //o.put("stops", r.getStops());
        o.put("isActive", r.isActive());
        return o;
    }

    private RouteDTO parseBody(HttpServletRequest req) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());
        RouteDTO r = new RouteDTO();
        if (json.containsKey("id"))     r.setId(((Long) json.get("id")).intValue());
        r.setRouteNumber((String) json.get("routeNumber"));
        r.setRouteName((String) json.get("routeName"));
        r.setSourceStop((String) json.get("sourceStop"));
        r.setDestinationStop((String) json.get("destinationStop"));
        r.setTotalDistanceKm(((Number) json.get("totalDistanceKm")).doubleValue());
        r.setFare(((Number) json.get("fare")).doubleValue());
        r.setTotalStops(((Long) json.get("totalStops")).intValue());
       // r.setStops((String) json.get("stops"));
        r.setActive(json.containsKey("isActive") && (Boolean) json.get("isActive"));
        return r;
    }
}

