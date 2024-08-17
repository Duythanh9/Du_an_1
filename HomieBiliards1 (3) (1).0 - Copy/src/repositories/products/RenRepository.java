/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.Ren;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class RenRepository implements ReadAndCreateOnlyRepository<Ren> {

    public List<Ren> selectAll() {
        List<Ren> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                            ,[ma]
                       FROM [dbo].[ren]
                     order by id desc
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(Ren.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean create(Ren newRen) {
        String sql = """
                     INSERT INTO [dbo].[ren]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newRen.getTen());
            stmt.setObject(2, newRen.getMa());

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
