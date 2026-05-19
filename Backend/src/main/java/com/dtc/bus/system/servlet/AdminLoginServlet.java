package com.dtc.bus.system.servlet;

import java.io.IOException;

import com.dtc.bus.system.dto.AdminDTO;
import com.dtc.dao.AdminDAO;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    private final AdminDAO dao = new AdminDAO();

    
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.getWriter().write(
                "Admin Login API Working"
        );
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        try {

            System.out.println("POST HIT");

            Gson gson = new Gson();

            AdminDTO admin =
                    gson.fromJson(
                            request.getReader(),
                            AdminDTO.class
                    );

            System.out.println(
                    "Username = " +
                    admin.getUsername()
            );

            System.out.println(
                    "Password = " +
                    admin.getPassword()
            );

            boolean valid =
                    dao.login(
                            admin.getUsername(),
                            admin.getPassword()
                    );

            System.out.println(
                    "DB Result = " +
                    valid
            );

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    "{\"success\":" +
                    valid +
                    "}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    "{\"success\":false}"
            );
        }
    }
}