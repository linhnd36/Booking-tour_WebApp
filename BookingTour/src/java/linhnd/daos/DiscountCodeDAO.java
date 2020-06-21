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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.naming.NamingException;
import linhnd.dbs.MyConnection;
import linhnd.dtos.DiscountCodeDTO;

/**
 *
 * @author PC
 */
public class DiscountCodeDAO implements Serializable {

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

    public List<DiscountCodeDTO> getLisDiscountCode() throws SQLException, NamingException, ParseException {
        List<DiscountCodeDTO> result = new ArrayList<>();
        DiscountCodeDTO dto = null;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = " SELECT DiscountId,Name,PercentDis,ExpiryDate,ImagesLink FROM dbo.DiscountCode WHERE ExpiryDate >= ?  ";
                stm = conn.prepareStatement(sql);
                Date date = new Date(System.currentTimeMillis());
                stm.setDate(1, date);
                rs = stm.executeQuery();
                while (rs.next()) {
                    dto = new DiscountCodeDTO();
                    dto.setDiscountId(rs.getString("DiscountId"));
                    dto.setExpiryDate(new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("ExpiryDate")));
                    dto.setImagesLink(rs.getString("ImagesLink"));
                    dto.setName(rs.getString("Name"));
                    dto.setPercentDis(rs.getInt("PercentDis"));
                    result.add(dto);
                }
            }
        } finally {
            close();
        }
        return result;
    }

    public DiscountCodeDTO getOneDiscountCode(String DiscountID) throws SQLException, NamingException {
        DiscountCodeDTO dto = null;
        try {
            conn = MyConnection.getConnection();
            if (conn != null) {
                String sql = " SELECT DiscountId,Name,PercentDis,ExpiryDate,ImagesLink FROM dbo.DiscountCode WHERE DiscountId = ? AND ExpiryDate > ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, DiscountID);
                Date date = new Date(System.currentTimeMillis());
                stm.setDate(2, date);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto = new DiscountCodeDTO();
                    dto.setDiscountId(rs.getString("DiscountId"));
                    dto.setExpiryDate(new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("ExpiryDate")));
                    dto.setImagesLink(rs.getString("ImagesLink"));
                    dto.setName(rs.getString("Name"));
                    dto.setPercentDis(rs.getInt("PercentDis"));
                }
            }
        } finally {
            close();
        }
        return dto;
    }

}
