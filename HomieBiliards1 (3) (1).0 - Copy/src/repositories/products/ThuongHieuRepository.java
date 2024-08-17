/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.ThuongHieu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class ThuongHieuRepository implements ReadAndCreateOnlyRepository<ThuongHieu> {
    
    public List<ThuongHieu> selectAll() {
        List<ThuongHieu> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                     ,[ma]
                       FROM [dbo].[thuong_hieu]
                     order by id desc
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(ThuongHieu.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
    
    public boolean create(ThuongHieu newThuongHieu) {
        String sql = """
                     INSERT INTO [dbo].[thuong_hieu]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newThuongHieu.getTen());
            stmt.setObject(2, newThuongHieu.getMa());
            
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
