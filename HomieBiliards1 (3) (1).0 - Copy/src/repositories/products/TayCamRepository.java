/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.Chuoi;
import entities.products.TayCam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class TayCamRepository implements ReadAndCreateOnlyRepository<TayCam> {
    
    public List<TayCam> selectAll() {
        List<TayCam> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                     ,[ma]
                       FROM [dbo].[tay_cam]
                     order by id desc
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(TayCam.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
    
    public boolean create(TayCam newTayCam) {
        String sql = """
                     INSERT INTO [dbo].[tay_cam]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newTayCam.getTen());
            stmt.setString(2, newTayCam.getMa());
            
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
