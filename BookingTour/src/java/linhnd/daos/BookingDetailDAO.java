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
public class BookingDetailDAO implements Serializable {

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

    public int countQuantityBooking(String TourId) throws SQLException, NamingException {
        int result = -1;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(TourID) AS Number FROM dbo.BookingDetail WHERE TourID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, TourId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("Number");
                }
            }
        } finally {
            close();
        }
        return result;
    }
}
