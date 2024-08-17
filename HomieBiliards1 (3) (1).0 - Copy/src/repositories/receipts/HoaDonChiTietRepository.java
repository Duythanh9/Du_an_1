/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories.receipts;

import config.DBConnect;
import entities.receipts.GiamGiaHoaDon;
import entities.receipts.HoaDonChiTiet;
import entities.receipts.GioHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import repositories.FullRepository;
import responses.HoaDonChiTietResponse;
import utils.DateConverter;

/**
 *
 * @author Mtt
 */
public class HoaDonChiTietRepository implements FullRepository<HoaDonChiTiet> {

    /*
    Order status ids:
    0 - created, transcation incomplete
    1 - trasaction completed
    2 - orde cancelled
     */
    @Override
    public List<HoaDonChiTiet> selectAll() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_hoa_don]
                           ,[id_san_pham_chi_tiet]
                           ,[so_luong]
                           ,[gia_ban]
                           ,[trang_thai]
                       FROM [dbo].[hoa_don_chi_tiet]
                       where trang_thai = 1
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(HoaDonChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idHoaDon(rs.getInt("id_hoa_don"))
                        .idSanPhamChiTiet(rs.getInt("id_san_pham_chi_tiet"))
                        .soLuong(rs.getInt("so_luong"))
                        .giaBan(rs.getInt("gia_ban"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public ArrayList<HoaDonChiTietResponse> getAll(Integer hoaDonID) {
        String sql = """
             SELECT       dbo.hoa_don_chi_tiet.id, dbo.san_pham_chi_tiet.ma_san_pham, dbo.san_pham_chi_tiet.ten_san_pham, dbo.san_pham_chi_tiet.mo_ta,
                     dbo.san_pham_chi_tiet.ngay_tao, dbo.san_pham_chi_tiet.trang_thai, 
                                     dbo.san_pham_chi_tiet.duong_kinh_dau, dbo.san_pham_chi_tiet.trong_luong, dbo.mau_sac.ten, dbo.chuoi.ten AS Expr1,
                     dbo.tay_cam.ten AS Expr2, dbo.dau_co_ban.ten AS Expr3, dbo.ngon.ten AS Expr4, dbo.ren.ten AS Expr5, 
                                     dbo.thuong_hieu.ten AS Expr6, dbo.xuat_xu.ten AS Expr7,
            						dbo.hoa_don_chi_tiet.so_luong,
                                  dbo.hoa_don_chi_tiet.gia_ban, dbo.hoa_don_chi_tiet.so_luong * dbo.hoa_don_chi_tiet.gia_ban
                    FROM        dbo.hoa_don_chi_tiet INNER JOIN
                                dbo.san_pham_chi_tiet ON dbo.hoa_don_chi_tiet.id_san_pham_chi_tiet = dbo.san_pham_chi_tiet.id INNER JOIN
                                dbo.mau_sac ON dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id INNER JOIN
                                dbo.chuoi ON dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id INNER JOIN
                                dbo.tay_cam ON dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id INNER JOIN
                                dbo.dau_co_ban ON dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id INNER JOIN
                                dbo.ngon ON dbo.san_pham_chi_tiet.ngon = dbo.ngon.id INNER JOIN
                                dbo.ren ON dbo.san_pham_chi_tiet.ren = dbo.ren.id INNER JOIN
                                dbo.thuong_hieu ON dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id INNER JOIN
                                dbo.xuat_xu ON dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id INNER JOIN
                                dbo.san_pham ON dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id
                    Where dbo.hoa_don_chi_tiet.id_san_pham_chi_tiet = dbo.san_pham_chi_tiet.id
                           AND       
                    dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id
                         AND
                      dbo.san_pham_chi_tiet.id_san_pham = dbo.san_pham.id 
                        AND
                         dbo.san_pham_chi_tiet.mau_sac = dbo.mau_sac.id 
                        AND
                         dbo.san_pham_chi_tiet.chuoi = dbo.chuoi.id 
                        AND
                         dbo.san_pham_chi_tiet.tay_cam = dbo.tay_cam.id 
                        AND
                         dbo.san_pham_chi_tiet.dau_co_ban = dbo.dau_co_ban.id 
                        AND
                         dbo.san_pham_chi_tiet.ngon = dbo.ngon.id 
                        AND                 
                        dbo.san_pham_chi_tiet.ren = dbo.ren.id 
                        AND
                         dbo.san_pham_chi_tiet.thuong_hieu = dbo.thuong_hieu.id 
                        AND
                         dbo.san_pham_chi_tiet.xuat_xu = dbo.xuat_xu.id 
                        AND                      
                         dbo.hoa_don_chi_tiet.id_hoa_don = ?    
                     """;

        ArrayList<HoaDonChiTietResponse> list = new ArrayList<>();

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hoaDonID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTietResponse response = HoaDonChiTietResponse.builder()
                        .id(rs.getInt(1))
                        .maSP(rs.getString(2))
                        .tenSP(rs.getString(3))
                        .moTa(rs.getString(4))
                        .ngayTao(rs.getDate(5))
                        .trangThai(rs.getInt(6))
                        .duongKinhDau(rs.getFloat(7))
                        .trongLuong(rs.getInt(8))
                        .mauSac(rs.getString(9))
                        .chuoi(rs.getString(10))
                        .tayCam(rs.getString(11))
                        .dauCoBan(rs.getString(12))
                        .ngon(rs.getString(13))
                        .ren(rs.getString(14))
                        .thuongHieu(rs.getString(15))
                        .xuatXu(rs.getString(16))
                        .soLuong(rs.getInt(17))
                        .giaBan(rs.getInt(18))
                        .thanhTien(rs.getDouble(19))
                        .build();

                list.add(response);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);

        }
        return list;
    }

   
    public List<GioHang> selectHDCT(String id) {
        List<GioHang> list = new ArrayList<>();
        String sql = """
SELECT        dbo.hoa_don.ma_hoa_don, dbo.san_pham_chi_tiet.ma_san_pham, dbo.san_pham.ten_san_pham, dbo.san_pham_chi_tiet.bao_hanh, dbo.san_pham_chi_tiet.gia_ban, dbo.hoa_don_chi_tiet.so_luong, dbo.san_pham_chi_tiet.gia_ban*dbo.hoa_don_chi_tiet.so_luong as thanh_tien
FROM            dbo.hoa_don INNER JOIN
                         dbo.hoa_don_chi_tiet ON dbo.hoa_don.id = dbo.hoa_don_chi_tiet.id_hoa_don INNER JOIN
                         dbo.san_pham ON dbo.hoa_don.id = dbo.san_pham.id INNER JOIN
                         dbo.san_pham_chi_tiet ON dbo.hoa_don_chi_tiet.id_san_pham_chi_tiet = dbo.san_pham_chi_tiet.id AND dbo.san_pham.id = dbo.san_pham_chi_tiet.id_san_pham
                     WHERE dbo.hoa_don.ma_hoa_don = ?
                                          """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(GioHang.builder()
                        .tenSanPham(rs.getString("ten_san_pham"))
                        .maSanPham(rs.getString("ma_san_pham"))
                        .soLuong(rs.getInt("so_luong"))
                        .donGia(rs.getInt("gia_ban"))
                        .baoHanh(rs.getInt("bao_hanh"))
                        .thanhTien(rs.getInt("thanh_tien"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    @Override
    public List<HoaDonChiTiet> selectDeleted() {

        List<HoaDonChiTiet> list = new ArrayList<>();
        String sql = """
                     SELECT [id]
                           ,[id_hoa_don]
                           ,[id_san_pham_chi_tiet]
                           ,[so_luong]
                           ,[gia_ban]
                           ,[trang_thai]
                       FROM [dbo].[hoa_don_chi_tiet]
                       where trang_thai = 0
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(HoaDonChiTiet.builder()
                        .id(rs.getInt("id"))
                        .idHoaDon(rs.getInt("id_hoa_don"))
                        .idSanPhamChiTiet(rs.getInt("id_san_pham_chi_tiet"))
                        .soLuong(rs.getInt("so_luong"))
                        .giaBan(rs.getInt("gia_ban"))
                        .trangThai(rs.getInt("trang_thai"))
                        .build());
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }

    }

    @Override
    public boolean create(HoaDonChiTiet newHoaDonChiTiet) {
        String sql = """
                     INSERT INTO [dbo].[hoa_don_chi_tiet]
                                ([id_hoa_don]
                                ,[id_san_pham_chi_tiet]
                                ,[so_luong]
                                ,[gia_ban])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?)
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, newHoaDonChiTiet.getIdHoaDon());
            stmt.setObject(2, newHoaDonChiTiet.getIdSanPhamChiTiet());
            stmt.setObject(3, newHoaDonChiTiet.getSoLuong());
            stmt.setObject(4, newHoaDonChiTiet.getGiaBan());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                throw new RuntimeException("Them that bai");
            }
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Loi db");

        }
    }

    public boolean add(HoaDonChiTietResponse response) {
        int check = 0;
        String sql = """
                     INSERT INTO [dbo].[hoa_don_chi_tiet]
                                ([id_hoa_don]
                                ,[id_san_pham_chi_tiet]
                                ,[so_luong]
                                ,[gia_ban])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?)
                     
                     """;

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, response.getIdHD());
            ps.setObject(2, response.getIdSPCT());
            ps.setObject(3, response.getSoLuong());
            ps.setObject(4, response.getGiaBan());

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);

        }
        return check > 0;
    }

    public boolean createTK(int id, int idsp, int sl, int gb) {
        String sql = """
                     INSERT INTO [dbo].[hoa_don_chi_tiet]
                                ([id_hoa_don]
                                ,[id_san_pham_chi_tiet]
                                ,[so_luong]
                                ,[gia_ban])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?)
                     
                     """;

        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, id);
            stmt.setObject(2, idsp);
            stmt.setObject(3, sl);
            stmt.setObject(4, gb);

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
    public boolean update(HoaDonChiTiet hoaDonChiTiet, int id) {
        String sql = """
                     UPDATE [dbo].[hoa_don_chi_tiet]
                        SET [id_hoa_don] = ?
                           ,[id_san_pham_chi_tiet] = ?
                           ,[so_luong] = ?
                           ,[gia_ban] = ?
                           ,[trang_thai] = ?
                      WHERE id = ?
                     
             """;
        try {
            PreparedStatement stmt = DBConnect.getConnection().prepareStatement(sql);
            stmt.setObject(1, hoaDonChiTiet.getIdHoaDon());
            stmt.setObject(2, hoaDonChiTiet.getIdSanPhamChiTiet());
            stmt.setObject(3, hoaDonChiTiet.getSoLuong());
            stmt.setObject(4, hoaDonChiTiet.getGiaBan());

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
                     UPDATE [dbo].[hoa_don_chi_tiet]
                        SET [trang_thai] = 0
                      WHERE id=?
                     
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
