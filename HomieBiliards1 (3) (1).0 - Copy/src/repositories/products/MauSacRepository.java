/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.products;

import config.DBConnect;
import entities.products.MauSac;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;

/**
 *
 * @author Mtt
 */
public class MauSacRepository implements ReadAndCreateOnlyRepository<MauSac> {

    @Override
    public List<MauSac> selectAll() {
        List<MauSac> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten]
                           ,[ma]
                       FROM [dbo].[mau_sac]
                     order by id desc
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(MauSac.builder().id(rs.getInt("id")).ten(rs.getString("ten")).build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean create(MauSac newMauSac) {
        String sql = """
                     INSERT INTO [dbo].[mau_sac]
                                ([ten],
                                                     [ma])
                          VALUES
                                (?,?)
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newMauSac.getTen());
            stmt.setString(2, newMauSac.getMa());

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
