/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.DotGiamGia;
import entities.receipts.DotGiamGiaChiTiet;
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
public class DotGiamGiaChiTietRepository implements FullRepository<DotGiamGiaChiTiet> {

    @Override
    public List<DotGiamGiaChiTiet> selectAll() {

        List<DotGiamGiaChiTiet> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                          ,[id_dot_giam_gia]
                          ,[id_san_pham_chi_tiet]
                          ,[ngay_bat_dau]
                          ,[ngay_ket_thuc]
                          ,[giam_phan_tram_trung_binh]
                          ,[trang_thai]
                      FROM [dbo].[dot_giam_gia_chi_tiet]
                      where trang_thai = 1
                    
                    
                    """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(DotGiamGiaChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idDotGiamGia(rs.getInt("id_dot_giam_gia"))
                        .idSanPhamChiTiet(rs.getInt("id_san_pham_chi_tiet"))
                        .ngayBatDau(rs.getDate("ngay_bat_dau"))
                        .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                        .giamPhanTramTrungBinh(rs.getDouble("giam_phan_tram_trung_binh"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public List<DotGiamGiaChiTiet> selectDeleted() {

        List<DotGiamGiaChiTiet> list = new ArrayList<>();
        String sql = """
                    SELECT [id]
                          ,[id_dot_giam_gia]
                          ,[id_san_pham_chi_tiet]
                          ,[ngay_bat_dau]
                          ,[ngay_ket_thuc]
                          ,[giam_phan_tram_trung_binh]
                          ,[trang_thai]
                      FROM [dbo].[dot_giam_gia_chi_tiet]
                      where trang_thai = 0
                    
                    
                    """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(DotGiamGiaChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idDotGiamGia(rs.getInt("id_dot_giam_gia"))
                        .idSanPhamChiTiet(rs.getInt("id_san_pham_chi_tiet"))
                        .ngayBatDau(rs.getDate("ngay_bat_dau"))
                        .ngayKetThuc(rs.getDate("ngay_ket_thuc"))
                        .giamPhanTramTrungBinh(rs.getDouble("giam_phan_tram_trung_binh"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public boolean create(DotGiamGiaChiTiet newDotGiamGiaChiTiet) {

        String sql = """
             INSERT INTO [dbo].[dot_giam_gia_chi_tiet]
                        ([id_dot_giam_gia]
                        ,[id_san_pham_chi_tiet]
                        ,[ngay_bat_dau]
                        ,[ngay_ket_thuc]
                        ,[giam_phan_tram_trung_binh])
                  VALUES
                        (?
                        ,?
                        ,?
                        ,?
                        ,?)
             
             """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, newDotGiamGiaChiTiet.getIdDotGiamGia());
            stmt.setObject(2, newDotGiamGiaChiTiet.getIdSanPhamChiTiet());
            stmt.setObject(3, DateConverter.dateToString(newDotGiamGiaChiTiet.getNgayBatDau()));
            stmt.setObject(4, DateConverter.dateToString(newDotGiamGiaChiTiet.getNgayKetThuc()));
            stmt.setObject(5, newDotGiamGiaChiTiet.getGiamPhanTramTrungBinh());

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
    public boolean update(DotGiamGiaChiTiet dotGiamGiaChiTiet, int id) {

        String sql = """
                 UPDATE [dbo].[dot_giam_gia_chi_tiet]
                        SET [id_dot_giam_gia] = ?
                           ,[id_san_pham_chi_tiet] = ?
                           ,[ngay_bat_dau] = ?
                           ,[ngay_ket_thuc] = ?
                           ,[giam_phan_tram_trung_binh] = ?
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, dotGiamGiaChiTiet.getIdDotGiamGia());
            stmt.setObject(2, dotGiamGiaChiTiet.getIdSanPhamChiTiet());
            stmt.setObject(3, DateConverter.dateToString(dotGiamGiaChiTiet.getNgayBatDau()));
            stmt.setObject(4, DateConverter.dateToString(dotGiamGiaChiTiet.getNgayKetThuc()));
            stmt.setObject(5, dotGiamGiaChiTiet.getGiamPhanTramTrungBinh());

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
                     UPDATE [dbo].[dot_giam_gia_chi_tiet]
                        SET [trang_thai] = 0
                      WHERE id = ?
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
   

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
