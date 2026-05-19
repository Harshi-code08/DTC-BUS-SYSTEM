package com.dtc.bus.system.servlet;

import com.dtc.bus.system.dto.BusDTO;
import com.dtc.bus.system.service.BusService;
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

@WebServlet("/api/buses")
public class BusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final BusService busService = new BusService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
//        res.setHeader("Access-Control-Allow-Origin", "*");
//        res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
//        res.setStatus(HttpServletResponse.SC_OK);
    }

    // ── GET ─────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");

            if (idParam != null && !idParam.isEmpty()) {
                BusDTO bus = busService.getBusById(Integer.parseInt(idParam));
                JsonUtil.sendJson(res, busToJson(bus).toJSONString());
            } else {
                List<BusDTO> buses = busService.getAllBuses();
                JSONArray arr = new JSONArray();

                for (BusDTO b : buses) {
                    arr.add(busToJson(b));
                }

                JsonUtil.sendJson(res, arr.toJSONString());
            }

        } catch (Exception e) {
            JsonUtil.sendError(res, 500, e.getMessage());
        }
    }

    // ── POST ────────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            BusDTO bus = parseBody(req);
            int id = busService.addBus(bus);

            JsonUtil.sendJson(res,
                    "{\"id\":" + id + ",\"message\":\"Bus added successfully\"}");

        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    // ── PUT ─────────────────────────────────────────────────────
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            BusDTO bus = parseBody(req);

            if (bus.getId() == 0) {
                throw new Exception("ID is required for update");
            }

            busService.updateBus(bus);
            JsonUtil.sendSuccess(res, "Bus updated successfully");

        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    // ── DELETE ──────────────────────────────────────────────────
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                throw new Exception("ID is required for delete");
            }

            int id = Integer.parseInt(idParam);
            busService.deleteBus(id);

            JsonUtil.sendSuccess(res, "Bus deleted successfully");

        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    // ── Helpers ──────────────────────────────────────────────────
    private JSONObject busToJson(BusDTO b) {
        JSONObject o = new JSONObject();

        o.put("id", b.getId());
        o.put("busNumber", b.getBusNumber());
        o.put("busType", b.getBusType());
        o.put("capacity", b.getCapacity());
        o.put("driverName", b.getDriverName());
        o.put("driverContact", b.getDriverContact());
        o.put("isActive", b.isActive());

        return o;
    }

    private BusDTO parseBody(HttpServletRequest req) throws Exception {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        if (sb.length() == 0) {
            throw new Exception("Request body is empty");
        }

        JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());

        BusDTO bus = new BusDTO();

        if (json.containsKey("id")) {
            bus.setId(((Long) json.get("id")).intValue());
        }

        bus.setBusNumber((String) json.get("busNumber"));
        bus.setBusType((String) json.get("busType"));

        if (json.get("capacity") != null) {
            bus.setCapacity(((Long) json.get("capacity")).intValue());
        }

        bus.setDriverName((String) json.get("driverName"));
        bus.setDriverContact((String) json.get("driverContact"));

        bus.setActive(json.containsKey("isActive") &&
                Boolean.TRUE.equals(json.get("isActive")));

        return bus;
    }
}