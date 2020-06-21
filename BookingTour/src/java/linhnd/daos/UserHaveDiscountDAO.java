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

/**
 *
 * @author PC
 */
public class UserHaveDiscountDAO implements Serializable {

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

    public boolean checkDiscount(String discountCode, String UserId) throws SQLException, NamingException {
        boolean check = true;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = " SELECT * FROM dbo.UserHaveDiscount WHERE DiscountId = ? AND UserID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, discountCode);
                stm.setString(2, UserId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    check = false;
                }
            }
        } finally {
            close();
        }
        return check;
    }

    public boolean newUserUsesDiscount(String userId, String discountId) throws SQLException, NamingException {
        boolean check = true;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = " INSERT INTO dbo.UserHaveDiscount ( UserID, DiscountId ) "
                        + " VALUES  ( ? , ? ) ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, discountId);
                check = stm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return check;
    }
}
