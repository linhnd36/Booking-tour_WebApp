/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import linhnd.dbs.MyConnection;

/**
 *
 * @author PC
 */
public class BookingDAO implements Serializable {

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

    public boolean newBooking(String BookingId, String TotalMoney, String UserID, String DiscountId) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                if (DiscountId == null) {
                    String sql = " INSERT INTO dbo.Booking ( BookingId ,BookingDate ,TotalMoney ,StatusId ,UserID  ) "
                            + " VALUES  ( ? , ? , ? , ? , ? ) ";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, BookingId);
                    Date date = new Date(System.currentTimeMillis());
                    stm.setDate(2, date);
                    stm.setString(3, TotalMoney);
                    stm.setString(4, "NOPAYPAL");
                    stm.setString(5, UserID);
                } else {
                    String sql = " INSERT INTO dbo.Booking ( BookingId ,BookingDate ,TotalMoney ,StatusId ,UserID , DiscountId ) "
                            + " VALUES  ( ? , ? , ? , ? , ? , ? ) ";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, BookingId);
                    Date date = new Date(System.currentTimeMillis());
                    stm.setDate(2, date);
                    stm.setString(3, TotalMoney);
                    stm.setString(4, "NOPAYPAL");
                    stm.setString(5, UserID);
                    stm.setString(6, DiscountId);
                }

                check = stm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return check;
    }
}
