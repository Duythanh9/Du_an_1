/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import config.DBConnect;
import entities.products.Chuoi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import responses.ThuocTinhResponse;

/**
 *
 * @author Mtt
 */
public class ThuocTinhService {
    
    public List<ThuocTinhResponse> selectAllByTable(String table) {
        List<ThuocTinhResponse> list = new ArrayList<>();
        String sql = "SELECT [id],[ten],[ma] FROM " + table;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(ThuocTinhResponse.builder()
                        .id(rs.getInt("id"))
                        .ten(rs.getString("ten"))
                        .ma(rs.getString("ma"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
    
}
