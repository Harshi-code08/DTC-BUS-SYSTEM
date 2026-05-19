package com.dtc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dtc.bus.system.util.DBConnection;



public class AdminDAO {

    public boolean login(
            String username,
            String password
    ) {

        String sql =
                "SELECT * FROM admin_users " +
                "WHERE username=? " +
                "AND password=? " +
                "AND is_active=1";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}