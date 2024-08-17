/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.GiamGiaHoaDon;
import entities.receipts.LichSuHoaDon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.FullRepository;
import repositories.ReadAndCreateOnlyRepository;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class LichSuHoaDonRepository implements ReadAndCreateOnlyRepository<LichSuHoaDon> {


    public List<LichSuHoaDon> selectLSHD(String ma) {
        List<LichSuHoaDon> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.hoa_don.id, dbo.hoa_don.ma_hoa_don, dbo.lich_su_hoa_don.ngay_cap_nhat, dbo.lich_su_hoa_don.ghi_chu, dbo.lich_su_hoa_don.id AS Expr1, dbo.lich_su_hoa_don.id_tai_khoan
                     FROM            dbo.hoa_don INNER JOIN
                                              dbo.lich_su_hoa_don ON dbo.hoa_don.id = dbo.lich_su_hoa_don.id_hoa_don
                     WHERE dbo.hoa_don.ma_hoa_don = ?
             """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, ma);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(LichSuHoaDon.builder()
                        .id(rs.getInt("Expr1"))
                        .idHoaDon(rs.getInt("id"))
                        .idTaiKhoan(rs.getInt("id_tai_khoan"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ghiChu(rs.getString("ghi_chu"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean update(int id, String action) {
        String sql = """
                     INSERT INTO [dbo].[lich_su_hoa_don]
                                ([id_hoa_don]
                                ,[id_tai_khoan]
                                ,[ngay_cap_nhat]
                                ,[ghi_chu])
                          VALUES
                                (?
                                ,1
                                ,GETDATE()
                                ,?)
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
            stmt.setObject(2, action);
            
            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public List<LichSuHoaDon> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean create(LichSuHoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
