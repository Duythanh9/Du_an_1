/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.DotGiamGia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.FullRepository;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class DotGiamGiaRepository implements FullRepository<DotGiamGia> {

    @Override
    public List<DotGiamGia> selectAll() {

        List<DotGiamGia> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                          ,[ma]
                          ,[ten]
                          ,[mo_ta]
                          ,[giam_phan_tram]
                          ,[ngay_bat_dau]
                          ,[ngay_ket_thuc]
                          ,[ngay_tao]
                          ,[ngay_cap_nhat]
                          ,[ngay_xoa]
                          ,[trang_thai]
                      FROM [dbo].[dot_giam_gia]
                    where trang_thai = 1
                    
                    """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(DotGiamGia.builder()
                        .id(rs.getInt("id"))
                        .ma(rs.getString("ma"))
                        .ten(rs.getString("ten"))
                        .moTa(rs.getString("mo_ta"))
                        .giamPhanTram(rs.getDouble("giam_phan_tram"))
                        .ngayBatDau(rs.getDate("ngay_bat_dau"))
                        .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public List<DotGiamGia> selectDeleted() {

        List<DotGiamGia> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                          ,[ma]
                          ,[ten]
                          ,[mo_ta]
                          ,[giam_phan_tram]
                          ,[ngay_bat_dau]
                          ,[ngay_ket_thuc]
                          ,[ngay_tao]
                          ,[ngay_cap_nhat]
                          ,[ngay_xoa]
                          ,[trang_thai]
                      FROM [dbo].[dot_giam_gia]
                    where trang_thai = 0
                    
                    """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(DotGiamGia.builder()
                        .id(rs.getInt("id"))
                        .ma(rs.getString("ma"))
                        .ten(rs.getString("ten"))
                        .moTa(rs.getString("mo_ta"))
                        .giamPhanTram(rs.getDouble("giam_phan_tram"))
                        .ngayBatDau(rs.getDate("ngay_bat_dau"))
                        .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean create(DotGiamGia newDotGiamGia) {

        String sql = """
             INSERT INTO [dbo].[dot_giam_gia]
                        ([ma]
                        ,[ten]
                        ,[mo_ta]
                        ,[giam_phan_tram]
                        ,[ngay_bat_dau]
                        ,[ngay_ket_thuc]
                        ,[ngay_tao]
                        ,[ngay_cap_nhat])
                  VALUES
                        (?
                        ,?
                        ,?
                        ,?
                        ,?
                        ,?
                        ,?
                        ,?)
             
             """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, newDotGiamGia.getMa());
            stmt.setObject(2, newDotGiamGia.getTen());
            stmt.setObject(3, newDotGiamGia.getMoTa());
            stmt.setObject(4, newDotGiamGia.getGiamPhanTram());
            stmt.setObject(5, DateConverter.dateToString(newDotGiamGia.getNgayBatDau()));
            stmt.setObject(6, DateConverter.dateToString(newDotGiamGia.getNgayKetThuc()));
            stmt.setObject(7, DateConverter.getDateNow());
            stmt.setObject(8, DateConverter.getDateNow());

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
    public boolean update(DotGiamGia dotGiamGia, int id) {

        String sql = """
                 UPDATE [dbo].[dot_giam_gia]
                        SET [ma] = ?
                           ,[ten] = ?
                           ,[mo_ta] = ?
                           ,[giam_phan_tram] = ?
                           ,[ngay_bat_dau] = ?
                           ,[ngay_ket_thuc] = ?
                           ,[ngay_cap_nhat] = ?
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, dotGiamGia.getMa());
            stmt.setObject(2, dotGiamGia.getTen());
            stmt.setObject(3, dotGiamGia.getMoTa());
            stmt.setObject(4, dotGiamGia.getGiamPhanTram());
            stmt.setObject(5, DateConverter.dateToString(dotGiamGia.getNgayBatDau()));
            stmt.setObject(6, DateConverter.dateToString(dotGiamGia.getNgayKetThuc()));
            stmt.setObject(7, DateConverter.getDateNow());
            stmt.setObject(8, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }

    }

    @Override
    public boolean delete(int id) {

        String sql = """
                     UPDATE [dbo].[dot_giam_gia]
                        SET [ngay_xoa] = ?
                           ,[trang_thai] = 0
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }

    }
}
