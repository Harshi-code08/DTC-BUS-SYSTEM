package com.dtc.bus.system.service;



import com.dtc.dao.AdminDAO;

public class AdminService {

    private final AdminDAO dao =
            new AdminDAO();

    public boolean login(
            String username,
            String password
    ) {

        return dao.login(
                username,
                password
        );
    }
}
