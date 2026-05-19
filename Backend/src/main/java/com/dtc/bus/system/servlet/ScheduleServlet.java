package com.dtc.bus.system.servlet;


import com.dtc.bus.system.dto.ScheduleDTO;
import com.dtc.bus.system.service.ScheduleService;
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

@WebServlet("/api/schedules")
public class ScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    private final ScheduleService scheduleService = new ScheduleService();

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
            String idParam = req.getParameter("id");
            if (idParam != null) {
                ScheduleDTO s = scheduleService.getScheduleById(Integer.parseInt(idParam));
                JsonUtil.sendJson(res, schedToJson(s).toJSONString());
            } else {
                List<ScheduleDTO> list = scheduleService.getAllSchedules();
                JSONArray arr = new JSONArray();
                list.forEach(s -> arr.add(schedToJson(s)));
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
            ScheduleDTO s = parseBody(req);
            int id = scheduleService.addSchedule(s);
            JsonUtil.sendJson(res, "{\"id\":" + id + ",\"message\":\"Schedule added\"}");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            ScheduleDTO s = parseBody(req);
            scheduleService.updateSchedule(s);
            JsonUtil.sendSuccess(res, "Schedule updated");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            scheduleService.deleteSchedule(id);
            JsonUtil.sendSuccess(res, "Schedule deleted");
        } catch (Exception e) {
            JsonUtil.sendError(res, 400, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject schedToJson(ScheduleDTO s) {
        JSONObject o = new JSONObject();
        o.put("id", s.getId());
        o.put("routeId", s.getRouteId());
        o.put("busId", s.getBusId());
        o.put("departureTime", s.getDepartureTime());
        o.put("arrivalTime", s.getArrivalTime());
        o.put("scheduleDays", s.getScheduleDays());
        o.put("isActive", s.isActive());
        return o;
    }

    private ScheduleDTO parseBody(HttpServletRequest req) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        JSONObject json = (JSONObject) new JSONParser().parse(sb.toString());
        ScheduleDTO s = new ScheduleDTO();
        if (json.containsKey("id"))       s.setId(((Long) json.get("id")).intValue());
        s.setRouteId(((Long) json.get("routeId")).intValue());
        s.setBusId(((Long) json.get("busId")).intValue());
        s.setDepartureTime((String) json.get("departureTime"));
        s.setArrivalTime((String) json.get("arrivalTime"));
        s.setScheduleDays((String) json.get("scheduleDays"));
        s.setActive(json.containsKey("isActive") && (Boolean) json.get("isActive"));
        return s;
    }
}
