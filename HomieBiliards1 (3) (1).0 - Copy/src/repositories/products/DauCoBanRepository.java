/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.DauCoBan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class DauCoBanRepository implements ReadAndCreateOnlyRepository<DauCoBan> {
    
    @Override
    public List<DauCoBan> selectAll() {
        List<DauCoBan> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                           ,[ma]
                       FROM [dbo].[dau_co_ban]
                     order by id desc
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(DauCoBan.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");
            
        }
    }
    
    @Override
    public boolean create(DauCoBan newDauCoBan) {
        String sql = """
                     INSERT INTO [dbo].[dau_co_ban]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     """;
        
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newDauCoBan.getTen());
            stmt.setString(2, newDauCoBan.getMa());
            
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
