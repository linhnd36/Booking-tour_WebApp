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
        int result = 0;
        String quantity = null;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT Quantity FROM dbo.BookingDetail WHERE TourID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, TourId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    quantity = rs.getString("Quantity");
                    result = result + Integer.parseInt(quantity);
                }
            }
        } finally {
            close();
        }
        return result;
    }

    public boolean newBookingDetail(String tourId, String bookingId, String quantity) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = " INSERT INTO dbo.BookingDetail ( TourID, Quantity, BookingId ) "
                        + "VALUES  ( ? , ? , ?  ) ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, tourId);
                stm.setString(2, quantity);
                stm.setString(3, bookingId);
                check = stm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return check;
    }

}
