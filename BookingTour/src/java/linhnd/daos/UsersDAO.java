/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import linhnd.dbs.MyConnection;
import linhnd.dtos.UsersDTO;

/**
 *
 * @author PC
 */
public class UsersDAO implements Serializable {

    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    private void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (rs != null) {
            rs.close();
        }
    }

    public boolean checkLogin(String username, String password) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT UserID FROM dbo.Users WHERE UserID = ? AND Password = ? AND StatusId = 'ACTIVE' ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } finally {
            close();
        }
        return check;
    }

    public UsersDTO getUser(String username, String password) throws SQLException, NamingException {
        UsersDTO dto = new UsersDTO();
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT UserID,Name,RoleId FROM dbo.Users WHERE UserID = ? AND Password = ? AND StatusId = 'ACTIVE' ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto.setName(rs.getString("Name"));
                    dto.setUserID(rs.getString("UserID"));
                    dto.setRoleId(rs.getString("RoleId"));
                }
            }
        } finally { 
            close();
        }
        return dto;
    }
}
