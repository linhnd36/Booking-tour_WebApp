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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import linhnd.dbs.MyConnection;
import linhnd.dtos.ToursDTO;

/**
 *
 * @author PC
 */
public class ToursDAO implements Serializable {

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

    public boolean checkTourId(String idTour) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "SELECT TourID FROM dbo.Tours WHERE TourID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, idTour);
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

    public boolean newTour(ToursDTO dto) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO dbo.Tours ( TourID ,TourName , FromDate ,ToDate , DateImport ,Price , Place ,Quota ,ImageLink ,StatusId ) "
                        + " VALUES  ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getTourID());
                stm.setString(2, dto.getTourName());
                stm.setString(3, dto.getFromDate());
                stm.setString(4, dto.getToDate());
                stm.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                stm.setString(6, dto.getPrice());
                stm.setString(7, dto.getPlace());
                stm.setString(8, dto.getQuota());
                stm.setString(9, dto.getImageLink());
                stm.setString(10, "TOUR_ACTIVE");
                check = stm.executeUpdate() > 0;
            }
        } finally {
            close();
        }
        return check;
    }

    public List<ToursDTO> searchTour(String loaction, String dateFrom, String dateTo, String priceFrom, String priceTo) throws SQLException, NamingException {
        List<ToursDTO> result = null;
        ToursDTO dto = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                if (dateFrom.equals("noSearchDate") && dateTo.equals("noSearchDate")) {
                    String sql = "SELECT TourID,TourName,FromDate,ToDate,Price,Place,Quota,ImageLink"
                            + " FROM dbo.Tours WHERE Place LIKE ? AND StatusId = ? "
                            + " AND Price >= ? AND Price <= ? ";
                    stm = conn.prepareStatement(sql);
                    stm.setNString(1, "%" + loaction + "%");
                    stm.setString(2, "TOUR_ACTIVE");
                    stm.setString(3, priceFrom);
                    stm.setString(4, priceTo);
                } else {
                    String sql = "SELECT TourID,TourName,FromDate,ToDate,Price,Place,Quota,ImageLink "
                            + " FROM dbo.Tours WHERE Place LIKE ? AND StatusId = ? AND ? <= FromDate AND ToDate <= ? "
                            + "	AND Price >= ? AND Price <= ? ";
                    stm = conn.prepareStatement(sql);
                    stm.setNString(1, "%" + loaction + "%");
                    stm.setString(2, "TOUR_ACTIVE");
                    stm.setString(3, dateFrom);
                    stm.setString(4, dateTo);
                    stm.setString(5, priceFrom);
                    stm.setString(6, priceTo);
                    
                }
                rs = stm.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    dto = new ToursDTO();
                    dto.setTourID(rs.getString("TourID"));
                    dto.setTourName(rs.getString("TourName"));
                    dto.setFromDate(dateFormat.format(rs.getDate("FromDate")));
                    dto.setToDate(dateFormat.format(rs.getDate("ToDate")));
                    dto.setPrice(rs.getString("Price"));
                    dto.setPlace(rs.getString("Place"));
                    dto.setQuota(rs.getString("Quota"));
                    dto.setImageLink(rs.getString("ImageLink"));
                    result.add(dto);
                }
            }
        } finally {
            close();
        }
        return result;
    }

}
