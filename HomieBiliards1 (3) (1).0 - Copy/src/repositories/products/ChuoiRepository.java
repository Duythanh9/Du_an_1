/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.Chuoi;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class ChuoiRepository implements ReadAndCreateOnlyRepository<Chuoi> {

    @Override
    public List<Chuoi> selectAll() {
        List<Chuoi> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                           ,[ma]
                       FROM [dbo].[chuoi]
                     order by id desc
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(Chuoi.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean create(Chuoi newChuoi) {
        String sql = """
                     INSERT INTO [dbo].[chuoi]
                                ([ten],
                     [ma])
                          VALUES
                                (?,?);
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, newChuoi.getTen());
            stmt.setString(2, newChuoi.getMa());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");

            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Loi db");

        }
    }

}
