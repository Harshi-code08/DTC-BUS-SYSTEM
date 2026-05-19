package com.dtc.bus.system.util;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUtil {

    public static void sendJson(HttpServletResponse res, String json) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);
    }

    public static void sendError(HttpServletResponse res, int status, String message) throws IOException {
        res.setStatus(status);
        sendJson(res, "{\"error\":\"" + message + "\"}");
    }

    public static void sendSuccess(HttpServletResponse res, String message) throws IOException {
        sendJson(res, "{\"message\":\"" + message + "\"}");
    }
}