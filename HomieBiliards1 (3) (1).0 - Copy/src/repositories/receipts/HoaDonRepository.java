/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.GiamGiaHoaDon;
import entities.receipts.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.experimental.Helper;
import repositories.FullRepository;
import responses.HoaDonResponse;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class HoaDonRepository implements FullRepository<HoaDon> {

    @Override
    public List<HoaDon> selectAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.hoa_don.id, dbo.hoa_don.ma_hoa_don, dbo.tai_khoan.id ma_nhan_vien, dbo.hoa_don.ngay_tao, dbo.hoa_don.ngay_cap_nhat, dbo.hoa_don.ngay_xoa, dbo.hoa_don.gia_tri, dbo.hoa_don.so_tien_giam, 
                                                                   dbo.hoa_don.gia_tri_tong, dbo.hoa_don.ghi_chu, dbo.giam_gia_hoa_don.id AS ma_giam_gia, dbo.trang_thai_hoa_don.id AS trang_thai_hoa_don, dbo.khach_hang.id AS ma_khach_hang
                                          FROM            dbo.hoa_don INNER JOIN
                                                                   dbo.khach_hang ON dbo.hoa_don.id_khach_hang = dbo.khach_hang.id INNER JOIN
                                                                   dbo.trang_thai_hoa_don ON dbo.hoa_don.id_trang_thai_hoa_don = dbo.trang_thai_hoa_don.id INNER JOIN
                                                                   dbo.tai_khoan ON dbo.hoa_don.id_tai_khoan = dbo.tai_khoan.id INNER JOIN
                                                                   dbo.giam_gia_hoa_don ON dbo.hoa_don.id_giam_gia_hoa_don = dbo.giam_gia_hoa_don.id
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(HoaDon.builder()
                        .id(rs.getInt("id"))
                        //                        .maKhachHang(rs.getString("ma"))
                        .idKhachHang(rs.getInt("ma_khach_hang"))
                        //                        .maTaiKhoan(rs.getString("ma_nhan_vien"))
                        .idTaiKhoan(rs.getInt("ma_nhan_vien"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .giaTri(rs.getInt("gia_tri"))
                        .soTienGiam(rs.getInt("so_tien_giam"))
                        .giaTriTong(rs.getInt("gia_tri_tong"))
                        .ghiChu(rs.getString("ghi_chu"))
                        //                        .TrangThaiHoaDon(rs.getString("trang_thai_hoa_don"))
                        .idTrangThaiHoaDon(rs.getInt("trang_thai_hoa_don"))
                        //                        .GiamGiaHoaDon(rs.getString("ma_giam_gia"))
                        .idGiamGiaHoaDon(rs.getInt("ma_giam_gia"))
                        .maHoaDon(rs.getString("ma_hoa_don"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public List<HoaDon> getAllTrangThai(int trangThai) {
    List<HoaDon> list = new ArrayList<>();
    String sql = """
                 SELECT        dbo.hoa_don.id, dbo.hoa_don.ma_hoa_don, dbo.tai_khoan.id ma_nhan_vien, dbo.hoa_don.ngay_tao, dbo.hoa_don.ngay_cap_nhat, dbo.hoa_don.ngay_xoa, dbo.hoa_don.gia_tri, dbo.hoa_don.so_tien_giam, 
                                       dbo.hoa_don.gia_tri_tong, dbo.hoa_don.ghi_chu, dbo.giam_gia_hoa_don.id AS ma_giam_gia, dbo.trang_thai_hoa_don.id AS trang_thai_hoa_don, dbo.khach_hang.id AS ma_khach_hang
                                FROM  dbo.hoa_don INNER JOIN
                                       dbo.khach_hang ON dbo.hoa_don.id_khach_hang = dbo.khach_hang.id INNER JOIN
                                       dbo.trang_thai_hoa_don ON dbo.hoa_don.id_trang_thai_hoa_don = dbo.trang_thai_hoa_don.id INNER JOIN
                                       dbo.tai_khoan ON dbo.hoa_don.id_tai_khoan = dbo.tai_khoan.id INNER JOIN
                                       dbo.giam_gia_hoa_don ON dbo.hoa_don.id_giam_gia_hoa_don = dbo.giam_gia_hoa_don.id 
                                WHERE dbo.trang_thai_hoa_don.id = ?
                 """;

    try {
        PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
        stmt.setInt(1, trangThai); // Truyền giá trị trạng thái vào PreparedStatement

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            list.add(HoaDon.builder()
                    .id(rs.getInt("id"))
                    .idKhachHang(rs.getInt("ma_khach_hang"))
                    .idTaiKhoan(rs.getInt("ma_nhan_vien"))
                    .ngayTao(rs.getDate("ngay_tao"))
                    .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                    .ngayXoa(rs.getDate("ngay_xoa"))
                    .giaTri(rs.getInt("gia_tri"))
                    .soTienGiam(rs.getInt("so_tien_giam"))
                    .giaTriTong(rs.getInt("gia_tri_tong"))
                    .ghiChu(rs.getString("ghi_chu"))
                    .idTrangThaiHoaDon(rs.getInt("trang_thai_hoa_don"))
                    .idGiamGiaHoaDon(rs.getInt("ma_giam_gia"))
                    .maHoaDon(rs.getString("ma_hoa_don"))
                    .build());
        }
        return list;
    } catch (Exception e) {
        e.printStackTrace(); // In thông tin lỗi chi tiết
        throw new RuntimeException("Lỗi khi truy vấn cơ sở dữ liệu", e);
    }
}


    public List<HoaDon> search(String ma) {
        List<HoaDon> list = new ArrayList<>();
        String sql = """
                     SELECT        dbo.hoa_don.id, dbo.hoa_don.ma_hoa_don, dbo.tai_khoan.id ma_nhan_vien, dbo.hoa_don.ngay_tao, dbo.hoa_don.ngay_cap_nhat, dbo.hoa_don.ngay_xoa, dbo.hoa_don.gia_tri, dbo.hoa_don.so_tien_giam, 
                                                                   dbo.hoa_don.gia_tri_tong, dbo.hoa_don.ghi_chu, dbo.giam_gia_hoa_don.id AS ma_giam_gia, dbo.trang_thai_hoa_don.id AS trang_thai_hoa_don, dbo.khach_hang.id AS ma_khach_hang
                                          FROM            dbo.hoa_don INNER JOIN
                                                                   dbo.khach_hang ON dbo.hoa_don.id_khach_hang = dbo.khach_hang.id INNER JOIN
                                                                   dbo.trang_thai_hoa_don ON dbo.hoa_don.id_trang_thai_hoa_don = dbo.trang_thai_hoa_don.id INNER JOIN
                                                                   dbo.tai_khoan ON dbo.hoa_don.id_tai_khoan = dbo.tai_khoan.id INNER JOIN
                                                                   dbo.giam_gia_hoa_don ON dbo.hoa_don.id_giam_gia_hoa_don = dbo.giam_gia_hoa_don.id where ma_hoa_don like?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, "%" + ma + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(HoaDon.builder()
                        .id(rs.getInt("id"))
                        //                        .maKhachHang(rs.getString("ma"))
                        .idKhachHang(rs.getInt("ma_khach_hang"))
                        //                        .maTaiKhoan(rs.getString("ma_nhan_vien"))
                        .idTaiKhoan(rs.getInt("ma_nhan_vien"))
                        .ngayTao(rs.getDate("ngay_tao"))
                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
                        .ngayXoa(rs.getDate("ngay_xoa"))
                        .giaTri(rs.getInt("gia_tri"))
                        .soTienGiam(rs.getInt("so_tien_giam"))
                        .giaTriTong(rs.getInt("gia_tri_tong"))
                        .ghiChu(rs.getString("ghi_chu"))
                        //                        .TrangThaiHoaDon(rs.getString("trang_thai_hoa_don"))
                        .idTrangThaiHoaDon(rs.getInt("trang_thai_hoa_don"))
                        //                        .GiamGiaHoaDon(rs.getString("ma_giam_gia"))
                        .idGiamGiaHoaDon(rs.getInt("ma_giam_gia"))
                        .maHoaDon(rs.getString("ma_hoa_don"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public ArrayList<HoaDonResponse> getAll() {
        String sql = """
                  Select  dbo.hoa_don.id, dbo.hoa_don.id_khach_hang, dbo.hoa_don.id_tai_khoan,
                                                                    dbo.hoa_don.id_trang_thai_hoa_don, dbo.hoa_don.id_giam_gia_hoa_don,
                                                                    dbo.hoa_don.ngay_tao, dbo.hoa_don.ma_hoa_don, dbo.khach_hang.ma, 
                                                                    dbo.khach_hang.ten_khach_hang,dbo.khach_hang.dien_thoai, dbo.khach_hang.dia_chi,
                                                                    dbo.tai_khoan.ma , dbo.tai_khoan.chuc_vu, dbo.trang_thai_hoa_don.ten,
                                                                    dbo.giam_gia_hoa_don.ma , dbo.giam_gia_hoa_don.ten ,hoa_don.gia_tri_tong  
                                            
                                                                    From hoa_don, khach_hang, tai_khoan, trang_thai_hoa_don, giam_gia_hoa_don 
                     """;

        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse hoaDonResponse = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .idKH(rs.getInt(2))
                        .idTaiKhoan(rs.getInt(3))
                        .idTrangThaiHoaDon(rs.getInt(4))
                        .idGiamGiaHoaDon(rs.getInt(5))
                        .ngayTao(rs.getDate(6))
                        .maHoaDon(rs.getString(7))
                        .maKhachHang(rs.getString(8))
                        .tenKhachHang(rs.getString(9))
                        .dienThoai(rs.getString(10))
                        .diaChi(rs.getString(11))
                        .maTK(rs.getString(12))
                        .cvTK(rs.getString(13))
                        .trangThaiHoaDon(rs.getString(14))
                        .maGiamGiaHD(rs.getString(15))
                        .tenGiamGiaHD(rs.getString(16))
                        .giaTriTong(rs.getDouble(17))
                        .build();
                lists.add(hoaDonResponse);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }

    public boolean updateThongTin(HoaDonResponse hoaDon) {

        int check = 0;

        String sql = """
                    UPDATE [dbo].[hoa_don]
                       SET 
                          [id_trang_thai_hoa_don] = 2
                     WHERE id = ?
                                         
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Object la cha cua cac loai kieu du lieu 
//            ps.setObject(1, hoaDon.getTenKhachHang());
//            ps.setObject(3, hoaDon.getD()); // Nhan vien lay tu login
//            ps.setObject(2, hoaDon.getDiaChiNguoiNhan()); // Nhan vien lay tu login
            ps.setObject(1, hoaDon.getId()); // Nhan vien lay tu login

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return check > 0;
    }

    public ArrayList<HoaDonResponse> getAllByStatuss() {
        String sql = """
                   SELECT [dbo].[hoa_don].[id]
                                                   ,[dbo].[hoa_don].[id_tai_khoan]
                                                   ,[dbo].[hoa_don].[id_khach_hang]
                                                    ,[dbo].[hoa_don].[id_trang_thai_hoa_don]
                                                       ,[dbo].[hoa_don].[id_giam_gia_hoa_don]
                                                   ,[dbo].[hoa_don].[ngay_tao]
                                                   ,[dbo].[hoa_don].[ma_hoa_don]
                                                    ,[dbo].[khach_hang].ma
                                                    ,[dbo].[khach_hang].ten_khach_hang
                                                    ,[dbo].[tai_khoan].ma
                                                    ,[dbo].[tai_khoan].ho_ten
                                                   ,[dbo].[hoa_don].[so_tien_giam]
                                                   ,[dbo].[hoa_don].[gia_tri_tong]
                   				,[dbo].[khach_hang].[dien_thoai]
                                                 ,dbo.trang_thai_hoa_don.ten
                                                 ,[dbo].[khach_hang].dia_chi
                                               FROM [dbo].[hoa_don],[dbo].[khach_hang],[dbo].[tai_khoan],trang_thai_hoa_don  Where
                                               [dbo].[hoa_don].id_khach_hang = [dbo].[khach_hang].id AND
                                               [dbo].[hoa_don].id_tai_khoan = [dbo].[tai_khoan].id AND
                                               dbo.hoa_don.id_trang_thai_hoa_don = dbo.trang_thai_hoa_don.id AND
                                               [dbo].[hoa_don].id_trang_thai_hoa_don =  1
                     """;

        ArrayList<HoaDonResponse> lists = new ArrayList<>();
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonResponse hoaDonResponse = HoaDonResponse.builder()
                        .id(rs.getInt(1))
                        .idKH(rs.getInt(3))
                        .idTaiKhoan(rs.getInt(2))
                        .idTrangThaiHoaDon(rs.getInt(4))
                        .idGiamGiaHoaDon(rs.getInt(5))
                        .ngayTao(rs.getDate(6))
                        .maHoaDon(rs.getString(7))
                        .maKhachHang(rs.getString(8))
                        .tenKhachHang(rs.getString(9))
                        .maTK(rs.getString(10))
                        .tenTaiKhoan(rs.getString(11))
                        .dienThoai(rs.getString(14))
                        .diaChi(rs.getString(16))
                        .soTienGiam(rs.getInt(12))
                        //                        .maTK(rs.getString(10))
                        //                        .cvTK(rs.getString(13))
                        .trangThaiHoaDon(rs.getString(15))
                        //                        .maGiamGiaHD(rs.getString(15))
                        //                        .tenGiamGiaHD(rs.getString(16))
                        .giaTriTong(rs.getDouble(13))
                        .build();
                lists.add(hoaDonResponse);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return lists;
    }

    public boolean updateTongTien(HoaDonResponse hoaDon) {

        int check = 0;

        String sql = """
                 UPDATE [dbo].[hoa_don]
                        SET 
                           [gia_tri_tong] = ?
                      WHERE id =?
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Object la cha cua cac loai kieu du lieu 
            ps.setObject(1, hoaDon.getGiaTriTong());
            ps.setObject(2, hoaDon.getId()); // Nhan vien lay tu login

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return check > 0;
    }

    public boolean addHoaDon(HoaDon hoaDon) {

        int check = 0;
        String sql = """
                    INSERT INTO [dbo].[hoa_don]
                               ([ma_hoa_don]
                               ,[id_khach_hang]
                               ,[id_tai_khoan]
                               ,[ngay_tao]
                               ,[gia_tri_tong]
                               ,[id_trang_thai_hoa_don]
                               ,[id_giam_gia_hoa_don])
                         VALUES(?,?,?,?,?,?,1);
                    """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, utils.Helper.generateRandomMaHoaDon());
            ps.setObject(2, hoaDon.getIdKhachHang());
            ps.setObject(3, hoaDon.getIdTaiKhoan());
            ps.setObject(4, new Date());
            ps.setObject(5, 0);
            ps.setObject(6, 1);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return check > 0;
    }

    public List<HoaDon> selectFind(int id) {
//        List<HoaDon> list = new ArrayList<>();
//        String sql = """
//                     SELECT        dbo.hoa_don.id, dbo.hoa_don.ma_hoa_don, dbo.tai_khoan.ma AS ma_nhan_vien, dbo.hoa_don.ngay_tao, dbo.hoa_don.ngay_cap_nhat, dbo.hoa_don.ngay_xoa, dbo.hoa_don.gia_tri, dbo.hoa_don.so_tien_giam, 
//                                              dbo.hoa_don.gia_tri_tong, dbo.hoa_don.ghi_chu, dbo.giam_gia_hoa_don.ten AS ma_giam_gia, dbo.trang_thai_hoa_don.ten AS trang_thai_hoa_don, dbo.khach_hang.ma
//                     FROM            dbo.hoa_don INNER JOIN
//                                              dbo.khach_hang ON dbo.hoa_don.id_khach_hang = dbo.khach_hang.id INNER JOIN
//                                              dbo.trang_thai_hoa_don ON dbo.hoa_don.id_trang_thai_hoa_don = dbo.trang_thai_hoa_don.id INNER JOIN
//                                              dbo.tai_khoan ON dbo.hoa_don.id_tai_khoan = dbo.tai_khoan.id INNER JOIN
//                                              dbo.giam_gia_hoa_don ON dbo.hoa_don.id_giam_gia_hoa_don = dbo.giam_gia_hoa_don.id
//                     						 WHERE DBO.hoa_don.id_trang_thai_hoa_don = ?
//                     """;
//
//        try {
//            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
//            stmt.setObject(1, id);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                list.add(HoaDon.builder()
//                        .id(rs.getInt("id"))
//                        .maKhachHang(rs.getString("ma"))
//                        .maTaiKhoan(rs.getString("ma_nhan_vien"))
//                        .ngayTao(rs.getDate("ngay_tao"))
//                        .ngayCapNhat(rs.getDate("ngay_cap_nhat"))
//                        .ngayXoa(rs.getDate("ngay_xoa"))
//                        .giaTri(rs.getInt("gia_tri"))
//                        .soTienGiam(rs.getInt("so_tien_giam"))
//                        .giaTriTong(rs.getInt("gia_tri_tong"))
//                        .ghiChu(rs.getString("ghi_chu"))
//                        .trangThaiHoaDon(rs.getString("trang_thai_hoa_don"))
//                        .GiamGiaHoaDon(rs.getString("ma_giam_gia"))
//                        .maHoaDon(rs.getString("ma_hoa_don"))
//                        .build());
//            }
//            return list;
//        } catch (Exception e) {
//            throw new RuntimeException("Loi db");
//
//        }
        return selectAll();
    }

    @Override
    public boolean create(HoaDon newHoaDon) {
//        String sql = """
//                     INSERT INTO [dbo].[hoa_don]
//                                ([id_khach_hang]
//                                ,[id_tai_khoan]
//                                ,[ngay_tao]
//                                ,[ngay_cap_nhat]
//                                ,[gia_tri]
//                                ,[so_tien_giam]
//                                ,[gia_tri_tong]
//                                ,[ghi_chu]
//                                ,[id_trang_thai_hoa_don]
//                                ,[id_giam_gia_hoa_don]
//                                ,[ma_hoa_don])
//                          VALUES
//                                (?
//                                ,?
//                                ,?
//                                ,?
//                                ,?
//                                ,?
//                                ,?
//                                ,?
//                                ,?
//                                ,?)
//                     """;
//
//        try {
//            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
//
//            stmt.setObject(1, newHoaDon.getIdKhachHang());
//            stmt.setObject(2, newHoaDon.getIdTaiKhoan());
//            stmt.setObject(3, DateConverter.getDateNow());
//            stmt.setObject(4, DateConverter.getDateNow());
//            stmt.setObject(5, newHoaDon.getGiaTri());
//            stmt.setObject(6, newHoaDon.getSoTienGiam());
//            stmt.setObject(7, newHoaDon.getGiaTriTong());
//            stmt.setObject(8, newHoaDon.getGhiChu());
//            stmt.setObject(9, 2);
//            stmt.setObject(10, newHoaDon.getGiamGiaHoaDon());
//            stmt.setObject(11, newHoaDon.getMaHoaDon());
//
//            int affected = stmt.executeUpdate();
//
//            if (affected != 1) {
//                throw new RuntimeException("Them that bai");
//            }
//            return true;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Loi db");
//
//        }
        return addHoaDon(newHoaDon);
    }

    public boolean createTK(String ma) {
        String sql = """
                     INSERT INTO [dbo].[hoa_don]
                                ([ma_hoa_don]
                                ,[id_khach_hang]
                                ,[id_tai_khoan]
                                ,[ngay_tao]
                                ,[ngay_cap_nhat]
                                ,[ngay_xoa]
                                ,[gia_tri]
                                ,[so_tien_giam]
                                ,[gia_tri_tong]
                                ,[ghi_chu]
                                ,[id_trang_thai_hoa_don]
                                ,[id_giam_gia_hoa_don])
                          VALUES
                                (?
                                ,1
                                ,1
                                ,GETDATE()
                                ,null
                                ,null
                                ,0
                                ,0
                                ,0
                                ,'Don moi'
                                ,1
                                ,1)
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, ma);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean update(int idkh, String gc, int id) {

        String sql = """
                     UPDATE [dbo].[hoa_don]
                        SET [id_khach_hang] = ?
                           ,[ngay_cap_nhat] = ?
                           ,[ghi_chu] = ?
                     	  WHERE id = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, idkh);
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, gc);
            stmt.setObject(4, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }

    }

    public boolean updateHD(String ma, int idKH, int giam, String ghiChu, int idTT) {

        String sql = """
                     UPDATE [dbo].[hoa_don]
                        SET [id_khach_hang] = ?
                           ,[so_tien_giam] = ?
                           ,[ghi_chu] = ?
                           ,[id_trang_thai_hoa_don] = ?
                            ,[ngay_cap_nhat] = GETDATE()
                      WHERE ma_hoa_don = ?
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, idKH);
            stmt.setObject(2, giam);
            stmt.setObject(3, ghiChu);
            stmt.setObject(4, idTT);
            stmt.setObject(5, ma);

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
                     UPDATE [dbo].[hoa_don]
                        SET [ngay_xoa] = ?
                     ,[ngay_cap_nhat] = ?
                           ,[id_trang_thai_hoa_don] = 3
                      WHERE id = ?
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, id);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean deleteTK(String ma) {
        String sql = """
                     UPDATE [dbo].[hoa_don]
                        SET [ngay_xoa] = ?
                     ,[ngay_cap_nhat] = ?
                           ,[id_trang_thai_hoa_don] = 3
                      WHERE ma_hoa_don = ?
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, DateConverter.getDateNow());
            stmt.setObject(3, ma);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean thanhToan(String ma) {
        String sql = """
                     UPDATE [dbo].[hoa_don]
                        SET [ngay_xoa] = ?
                           ,[id_trang_thai_hoa_don] = 1
                      WHERE ma_hoa_don = ?
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, ma);

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Cap nhat that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean chuaThanhToan(String ma) {
        String sql = """
                     UPDATE [dbo].[hoa_don]
                        SET [ngay_xoa] = ?
                           ,[id_trang_thai_hoa_don] = 2
                      WHERE ma_hoa_don = ?
                     """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            stmt.setObject(1, DateConverter.getDateNow());
            stmt.setObject(2, ma);

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
    public boolean update(HoaDon entity, int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HoaDon> selectDeleted() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
