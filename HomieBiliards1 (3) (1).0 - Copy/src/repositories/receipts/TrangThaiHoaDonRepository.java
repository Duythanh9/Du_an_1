/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.TrangThaiHoaDon;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Mtt
 */
public class TrangThaiHoaDonRepository implements ReadAndCreateOnlyRepository<TrangThaiHoaDon> {
    
    @Override
    public List<TrangThaiHoaDon> selectAll() {
        List<TrangThaiHoaDon> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                           ,[ghi_chu]
                       FROM [dbo].[trang_thai_hoa_don]
                     
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(TrangThaiHoaDon.builder()
                        .id(rs.getInt("id"))
                        .ten(rs.getString("ten"))
                        .ghiChu(rs.getString("ghi_chu"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
        }
    }
    
    @Override
    public boolean create(TrangThaiHoaDon newTrangThaiHoaDon) {
        
        String sql = """
                    INSERT INTO [dbo].[trang_thai_hoa_don]
                                ([ten]
                                ,[ghi_chu])
                          VALUES
                                (?
                                ,?)
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newTrangThaiHoaDon.getTen());
            stmt.setObject(2, newTrangThaiHoaDon.getGhiChu());
            
            int affected = stmt.executeUpdate();
            
            if (affected != 1) {
                throw new RuntimeException("Them that bai");
                
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
    
}
