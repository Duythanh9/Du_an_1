/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.subjects;

import config.DBConnect;
import entities.subjects.KhachHang;
import entities.subjects.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DateConverter;
import repositories.FullRepository;

/**
 *
 * @author Mtt
 */
public class KhachHangRepository implements FullRepository<KhachHang> {

    @Override
    public List<KhachHang> selectAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten_khach_hang]
                           ,[email]
                           ,[dien_thoai]
                           ,[dia_chi]
                           ,[ghi_chu]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[nguoi_tao]
                           ,[nguoi_cap_nhat]
                           ,[trang_thai]
                           ,[ma]
                       FROM [dbo].[khach_hang]
                       where [trang_thai] = 1
                     
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(KhachHang.builder()
                        .id(rs.getInt("id"))
                        .tenKhachHang(rs.getString("ten_khach_hang"))
                        .email(rs.getString("email"))
                        .dienThoai(rs.getString("dien_thoai"))
                        .diaChi("dia_chi")
                        .ghiChu("ghi_chu")
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .nguoiTao(rs.getInt("nguoi_tao"))
                        .nguoiCapNhat(rs.getInt("nguoi_cap_nhat"))
                        .trangThai(rs.getInt("trang_thai"))
                        .ma(rs.getString("ma"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");
        }
    }

    public List<KhachHang> selectKH() {
        List<KhachHang> list = new ArrayList<>();
        String sql = """
                        SELECT * FROM khach_hang
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            KhachHang kh = new KhachHang();
            while (rs.next()) {
                list.add(KhachHang.builder()
                        .id(rs.getInt("id"))
                        .tenKhachHang(rs.getString("ten_khach_hang"))
                        .email(rs.getString("email"))
                        .dienThoai(rs.getString("dien_thoai"))
                        .diaChi("dia_chi")
                        .ghiChu("ghi_chu")
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .nguoiTao(rs.getInt("nguoi_tao"))
                        .nguoiCapNhat(rs.getInt("nguoi_cap_nhat"))
                        .trangThai(rs.getInt("trang_thai"))
                        .ma(rs.getString("ma"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public List<KhachHang> selectDeleted() {
        List<KhachHang> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[ten_khach_hang]
                           ,[email]
                           ,[dien_thoai]
                           ,[dia_chi]
                           ,[ghi_chu]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[nguoi_tao]
                           ,[nguoi_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                       FROM [dbo].[khach_hang]
                       where [trang_thai] = 0
                     
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(KhachHang.builder()
                        .id(rs.getInt("id"))
                        .tenKhachHang(rs.getString("ten_khach_hang"))
                        .email(rs.getString("email"))
                        .dienThoai(rs.getString("dien_thoai"))
                        .diaChi("dia_chi")
                        .ghiChu("ghi_chu")
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("nguoi_cap_nhat"))
                        .nguoiTao(rs.getInt("nguoi_tao"))
                        .nguoiCapNhat(rs.getInt("nguoi_cap_nhat"))
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
    public boolean create(KhachHang newKhachHang) {
        String sql = """
                     INSERT INTO [dbo].[khach_hang]
                                ([ten_khach_hang]
                                ,[email]
                                ,[dien_thoai]
                                ,[dia_chi]
                                ,[ghi_chu]
                                ,[ngay_tao]
                                ,[ngay_cap_nhat]
                                ,[nguoi_tao]
                                ,[nguoi_cap_nhat])
                          VALUES
                                (?
                                ,?
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

            stmt.setObject(1, newKhachHang.getTenKhachHang());
            stmt.setObject(1, newKhachHang.getEmail());
            stmt.setObject(1, newKhachHang.getDienThoai());
            stmt.setObject(1, newKhachHang.getDiaChi());
            stmt.setObject(1, newKhachHang.getGhiChu());
            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(1, newKhachHang.getNguoiTao());
            stmt.setObject(1, newKhachHang.getNguoiCapNhat());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");
        }
    }

    @Override
    public boolean update(KhachHang khachHang, int id) {

        String sql = """
                    UPDATE [dbo].[khach_hang]
                       SET [ten_khach_hang] = ?
                          ,[email] = ?
                          ,[dien_thoai] = ?
                          ,[dia_chi] = ?
                          ,[ghi_chu] = ?
                          ,[ngay_cap_nhat] = ?
                          ,[nguoi_cap_nhat] = ?
                     WHERE id = ?
                    
                     """;

        System.err.println(khachHang.getNguoiCapNhat());
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, khachHang.getTenKhachHang());
            stmt.setObject(2, khachHang.getEmail());
            stmt.setObject(3, khachHang.getDienThoai());
            stmt.setObject(4, khachHang.getDiaChi());
            stmt.setObject(5, khachHang.getGhiChu());
            stmt.setObject(6, DateConverter.getDateNow());
            stmt.setObject(7, khachHang.getNguoiCapNhat());
            stmt.setObject(8, id);

            int affected = stmt.executeUpdate();
            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi db");
        }

    }

    @Override
    public boolean delete(int id) {
        String sql = """
                       UPDATE [dbo].[khach_hang]
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

    public ArrayList<KhachHang> getALLKH() {
        String sql = """
                     SELECT [id]
                           ,[ten_khach_hang]
                           ,[email]
                           ,[dien_thoai]
                           ,[dia_chi]
                           ,[ghi_chu]
                           ,[ngay_tao]
                           ,[ngay_cap_nhat]
                           ,[nguoi_tao]
                           ,[nguoi_cap_nhat]
                           ,[ngay_xoa]
                           ,[trang_thai]
                           ,[ma]
                       FROM [dbo].[khach_hang] Where trang_thai = 1
                     """;
        ArrayList<KhachHang> list = new ArrayList<>();
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang khachHang = KhachHang.builder()
                        .id(rs.getInt(1))
                        .ma(rs.getString(13))
                        .tenKhachHang(rs.getString(2))
                        .email(rs.getString(3))
                        .dienThoai(rs.getString(4))
                        .diaChi(rs.getString(5))
                        .ngayTao(rs.getDate(7))
                        .trangThai(rs.getInt(12))
                        .build();
                list.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
