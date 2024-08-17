/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.Ngon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class NgonRepository  implements ReadAndCreateOnlyRepository<Ngon>{

    public List<Ngon> selectAll() {
        List<Ngon> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                           ,[ma]
                       FROM [dbo].[ngon]
                     
                     order by id desc
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(Ngon.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean create(Ngon newNgon) {
        String sql = """
                     INSERT INTO [dbo].[ngon]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newNgon.getTen());
            stmt.setString(2, newNgon.getMa());

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
