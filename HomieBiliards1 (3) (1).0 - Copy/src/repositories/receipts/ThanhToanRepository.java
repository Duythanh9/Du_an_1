/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.LichSuHoaDon;
import entities.receipts.ThanhToan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.ReadAndCreateOnlyRepository;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class ThanhToanRepository implements ReadAndCreateOnlyRepository<ThanhToan> {

    @Override
    public List<ThanhToan> selectAll() {
        List<ThanhToan> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_hoa_don]
                           ,[ngay_thanh_toan]
                           ,[so_tien_mat]
                           ,[so_tien_the]
                       FROM [dbo].[thanh_toan]
                     
                     
             """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(ThanhToan.builder()
                        .idHoaDon(rs.getInt("id_hoa_don"))
                        .ngayThanhToan(rs.getDate("ngay_thanh_toan"))
                        .soTienMat(rs.getInt("so_tien_mat"))
                        .soTienThe(rs.getInt("so_tien_the"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public List<ThanhToan> selectByY(int y) {
        List<ThanhToan> list = new ArrayList<>();
        String sql = """
                     SELECT 
                         YEAR(ngay_thanh_toan) AS Nam,
                         SUM(so_tien_mat) AS tien_mat,
                     	SUM(so_tien_the) AS tien_the
                     FROM 
                         [homie_billiard].[dbo].[thanh_toan] where YEAR(ngay_thanh_toan) = ?
                     GROUP BY 
                         YEAR(ngay_thanh_toan), 
                         MONTH(ngay_thanh_toan)
                     ORDER BY 
                         Nam
             """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, y);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(ThanhToan.builder()
//                        .idHoaDon(rs.getInt("id_hoa_don"))
//                        .ngayThanhToan(rs.getDate("ngay_thanh_toan"))
                        .soTienMat(rs.getInt("tien_mat"))
                        .soTienThe(rs.getInt("tien_the"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean create(ThanhToan thanhToan) {
        String sql = """
                     INSERT INTO [dbo].[thanh_toan]
                                ([id_hoa_don]
                                ,[ngay_thanh_toan]
                                ,[so_tien_mat]
                                ,[so_tien_the])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?
                     
                    """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, thanhToan.getIdHoaDon());
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, thanhToan.getSoTienMat());
            stmt.setObject(4, thanhToan.getSoTienThe());

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
